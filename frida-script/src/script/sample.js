Java.perform(function() {
  showSoInfos('libnative-lib.so');
  // ================ Java Hook ======================
  var Clazz = Java.use("java.lang.Class");
  var Exception = Java.use("java.lang.Exception");
  var String = Java.use("java.lang.String");

  var MainActivityClass = Java.use('com.min.ndk.sample.MainActivity');
  var UserClass = Java.use('com.min.ndk.sample.User');
  var DataManagerClass = Java.use('com.min.ndk.sample.DataManager');

  MainActivityClass.sayHello.overload("[Ljava.lang.String;").implementation = function(strArr) {
    log('call sayHello(),args len:'+strArr.length+', value:'+strArr);

    //        this.sayHello();
  };

  MainActivityClass.sayHello.overload('com.min.ndk.sample.User').implementation = function() {
    log('call sayHello(user)');
    log('params -> name=' + arguments[0].getName());
    var user = UserClass.$new('lele', "345");
    this.sayHello(user);
  };

  UserClass.$init.overload('java.lang.String', 'java.lang.String').implementation = function() {
    log('call user construction : ' + arguments[0] + '  ' + arguments[1]);
    //this.$init('jiale','123');
    this.$init(arguments[0], arguments[1]);
  }

  DataManagerClass.getLastUser.implementation = function() {
    log('call getLastUser');
    var user = UserClass.$new('lele', "345");

    //         user.setName('abc');

    //         var nameField = Java.cast(user.getClass(),Clazz).getDeclaredField('name');
    //         nameField.setAccessible(true);
    //         var name = nameField.get(user);
    //         log('before-1:'+name);
    //         nameField.set(user,"abc");
    //         log('after-1:'+user.name.value);

    log('before-2:' + user.name.value);
    user.name.value = 'abc';
    log('after-2:' + user.name.value);

    return user;
  }

  // Native Hook

  var addNativePointer = Module.findExportByName('libnative-lib.so', 'Java_com_min_ndk_sample_MainActivity_add');
  Interceptor.attach(addNativePointer, {
    onEnter: function(args) {
      log('native call onEnter add, args: ' + args[0] + ',  ' + args[1] + ',  ' + args[2].toInt32() + ',  ' + args[3].toInt32());
      args[3] = ptr(100); //修改入参值
    },
    onLeave: function(retval) {
      log('native call onLeave add, before retval: ' + retval.toInt32());
      retval.replace(10000); //改变返回值
    }
  });

  var loginNativePointer = Module.findExportByName('libnative-lib.so', 'Java_com_min_ndk_sample_MainActivity_login');
  Interceptor.attach(loginNativePointer, {
    onEnter: function(args) {
      var userName = Java.cast(args[2], String);
      var password = Java.cast(args[3], String);
      log('native call onEnter login,input args : userName=' + userName + '   password=' + password);
    },
    onLeave: function(retval) {
      log('native call onLeave login, before retval=', Java.cast(retval, String));
      var env = Java.vm.getEnv();
      var jstring = env.newStringUtf("success");
      retval.replace(ptr(jstring)); // 改变返回的jstring类型的值
      log('native call onLeave login, after retval=', Java.cast(retval, String));
    }
  });

  var soAddr = Module.findBaseAddress("libnative-lib.so");

  var strPtr = Memory.allocUtf8String('12345678');
  var args_0;

  var loginCheckPtr = Module.findExportByName('libnative-lib.so','_Z10loginCheckPcS_');
  log('loginCheckPtr address:'+loginCheckPtr)
  var loginCheckNativeFunc = new NativeFunction(loginCheckPtr,'int', ['pointer', 'pointer']);
  log('manual call native loginCheck returnValue='+loginCheckNativeFunc(Memory.allocUtf8String('minych1'),Memory.allocUtf8String('123')));

  //替换方法
  Interceptor.replace(loginCheckPtr, new NativeCallback(function(ptr0, ptr1) {
    ptr0 = Memory.allocUtf8String('12345678');
    ptr1 = Memory.allocUtf8String('abc');

    var userName = Memory.readUtf8String(ptr0);
    var password = Memory.readUtf8String(ptr1);
    log('replace native call loginCheck,change args : userName=' + userName + '   password=' + password);
    var returnValue = loginCheckNativeFunc(ptr0,ptr1);
    log('replace returnValue:'+returnValue);
    return returnValue;
  }, 'int', ['pointer', 'pointer']));

  var getUserNameMethodPtr = ptr(soAddr).add(0x000006D0);
  Interceptor.attach(getUserNameMethodPtr, {
    onEnter: function(args) {},
    onLeave: function(retval) {
      log('native call onLeave getUserNameMethodPtr, retval=' + Memory.readUtf8String(retval));
      retval.replace(strPtr); //改变指针类型返回值
    }
  });

  var getPasswordMethodPtr = ptr(soAddr).add(0x000006E0);
  //getPasswordMethodPtr = Module.findExportByName('libnative-lib.so', '_Z11getPasswordPc');

  Interceptor.attach(getPasswordMethodPtr, {
    onEnter: function(args) {
      log('native call onEnter getPasswordMethodPtr');
      args_0 = args[0].toString();
    },
    onLeave: function(retval) {
      //改变通过指针方式返回值
      var buffer = Memory.readByteArray(ptr(args_0), 10);
      log(buf2hex(buffer));
      buffer = hexToBuf('61 62 63 00 34 72 86 bf 00 00');
      Memory.writeByteArray(ptr(args_0), buffer);
      dump(ptr(args_0),64);
    }
  });

  //hook strcmp函数
  // Interceptor.attach(ptr(0xb72b7ae0), {
  //   onEnter: function(args) {
  //     log(Memory.readUtf8String(args[0])+'   '+Memory.readUtf8String(args[1]));
  //   },
  //   onLeave: function(retval) {
  //   }
  // });

});
