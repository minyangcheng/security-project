//frida -U -f com.min.app.sample -l ./src/script/hook.js --no-pause

var packageName = 'com.min.app.sample';
var placeHolderArray;

Java.performNow(function() {
  var Application = Java.use("android.app.Application");
  Application.onCreate.implementation = function() {
    this.onCreate();
    console.log('find application : ' + this);
    if (this.toString().indexOf(packageName) < 0) {
      //防止加壳后的app，两次调用到onCreate
      return;
    }
    var classloader = this.getClassLoader(); // 获取classloader
    Java.classFactory.loader = classloader; //替换classloader
    hook();
  }
});

function hook() {
  placeHolderArray = Java.array("java.lang.Object", []);

  var MainActivity = Java.use('com.min.app.sample.MainActivity');
  MainActivity.sayHello.overload("[Lcom.min.app.sample.User;").implementation = function(array) {
    console.log('hook sayHello succ');
    var classLoaderObj = this.getClassLoader();
    var pathListObj = getPathListObj(classLoaderObj);
    var JavaArray = Java.use("java.lang.reflect.Array");
    var dexElementsObj = getDexElementsObj(pathListObj);
    console.log("handle dexElements : " + dexElementsObj);

    for (var i = 0; i < JavaArray.getLength(dexElementsObj); i++) {
      var dexElement = JavaArray.get(dexElementsObj, i);
      var dexFileObj = getDexFileObj(dexElement);
      console.log('handle dexFile : ' + dexFileObj);

      var enumerations = getMethod("dalvik.system.DexFile", "entries").invoke(dexFileObj, placeHolderArray);
      console.log('handle enumerations : ' + enumerations);

      var hasMoreElementsMethod = getMethod("java.util.Enumeration", "hasMoreElements");

      while (true) {
        var flag = hasMoreElementsMethod.invoke(enumerations, placeHolderArray);
        if (flag.toString() == 'false') {
          break;
        }
        var nextElementMethod = getMethod("java.util.Enumeration", "nextElement");
        var clazzName = nextElementMethod.invoke(enumerations, placeHolderArray).toString();
        try {
            console.log(Java.classFactory.loader.loadClass(clazzName));
        } catch (e) {
          console.log(e);
        }
      }
    }
  }
}

function getMethod(className, methodName) {
  var method = Java.use(className).class.getDeclaredMethod(methodName, placeHolderArray);
  return method;
}

function getMethod_(obj, methodName) {
  var method = Java.cast(obj.getClass(), Java.use("java.lang.Class")).class.getDeclaredMethod(methodName, placeHolderArray);
  return method;
}

function getPathListObj(obj) {
  var field = Java.use("dalvik.system.BaseDexClassLoader").class.getDeclaredField("pathList");
  field.setAccessible(true);
  return field.get(obj);
}

function getDexElementsObj(obj) {
  var field = Java.use("dalvik.system.DexPathList").class.getDeclaredField("dexElements");
  field.setAccessible(true);
  return field.get(obj);
}

function getDexFileObj(obj) {
  var field = Java.use("dalvik.system.DexPathList$Element").class.getDeclaredField("dexFile");
  field.setAccessible(true);
  return field.get(obj);
}
