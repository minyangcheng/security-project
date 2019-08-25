
//  frida -U -f com.min.app.sample -l ./src/script/hook.js

Java.performNow(function() {
  var applicationClass = Java.use("android.app.Application");
  applicationClass.attachBaseContext.implementation = function(context) {
    this.attachBaseContext(context);
    console.log('find application : ',this);
    if(this.toString().indexOf('com.min.app.sample')>-1){
      var classloader = context.getClassLoader(); // 获取classloader
      Java.classFactory.loader = classloader;  //替换classloader
      hook();
    }
  }
});

function hook() {
  var MainActivityClass = Java.use('com.min.app.sample.MainActivity');
  MainActivityClass.sayHello.overload("[Ljava.lang.String;").implementation = function(strArr) {
    console.log('call sayHello(),args len:' + strArr.length + ', value:' + strArr);
  };
}
