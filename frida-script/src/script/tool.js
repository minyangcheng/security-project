//获取当前时间
function getNowDateStr() {
  var date = new Date();
  return date.getFullYear() + "-" + paddingLeft(date.getMonth() + 1) +
    "-" + paddingLeft(date.getDate()) + " " + paddingLeft(date.getHours()) +
    ":" + paddingLeft(date.getMinutes()) + ":" + paddingLeft(date.getSeconds());
}

//不足10，左补0
function paddingLeft(num) {
  if (num < 10) {
    num = '0' + num;
  }
  return num;
}

//打印日志，包含线程号和时间,并发送达到python控制端
function log() {
  var threadid = Process.getCurrentThreadId();
  var head = "[ threadId = " + threadid + " , time = " + getNowDateStr() + " ]";
  var message = Array.prototype.join.call(arguments, '');
  //console.log(head, message);
  send(JSON.stringify({
    head: head,
    message: message
  })); //发送到python端进行打印可以显示颜色
}

//打印堆栈
function showStacks() {
  Java.perform(function() {
    log(Java.use("android.util.Log").getStackTraceString(Java.use("java.lang.Exception").$new()));
  });
}

var antiFgets = function() {
  log("anti_fgets");
  var fgetsPtr = Module.findExportByName("libc.so", "fgets");
  var fgets = new NativeFunction(fgetsPtr, 'pointer', ['pointer', 'int', 'pointer']);
  Interceptor.replace(fgetsPtr, new NativeCallback(function(buffer, size, fp) {
    var retval = fgets(buffer, size, fp);
    var bufstr = Memory.readUtf8String(buffer);
    if (bufstr.indexOf("TracerPid:") > -1) {
      Memory.writeUtf8String(buffer, "TracerPid:\t0");
      // log("tracerpid replaced: " + Memory.readUtf8String(buffer));
    }
    return retval;
  }, 'pointer', ['pointer', 'int', 'pointer']));
};

var antiDebug = function(soName, funName) {
  var funPtr = null;
  funPtr = Module.findExportByName("xxxx.so", "p57F7418DCD0C22CD8909F9B22F0991D3");
  log("anti_antiDebug " + funcPtr);
  Interceptor.replace(funcPtr, new NativeCallback(function(pathPtr, flags) {
    log("anti ddddddddddddddebug LR: " + this.context.lr);
    return 0;
  }, 'int', ['int', 'int']));
};

//hook jni NewStringUTF
function hookNativeNewString() {
  var env = Java.vm.getEnv();
  var handlePointer = Memory.readPointer(env.handle);
  var NewStringUTFPtr = Memory.readPointer(handlePointer.add(0x29C));
  Interceptor.attach(NewStringUTFPtr, {
    onEnter: function(args) {
      log('NewStringUTFPtr args = ' + Memory.readUtf8String(args[1]))
    }
  });
}

//hook jni GetStringUTFChars
function hookNativeGetStringUTFChars() {
  var env = Java.vm.getEnv();
  var handlePointer = Memory.readPointer(env.handle);
  var GetStringUTFCharsPtr = Memory.readPointer(handlePointer.add(0x2A4));
  Interceptor.attach(GetStringUTFCharsPtr, {
    onEnter: function(args) {
      var str = "";
      Java.perform(function() {
        str = Java.cast(args[1], Java.use('java.lang.String'));
      });
      log("GetStringUTFChars args = " + str);
    }
  });
};

//查看so信息
function showSoInfos(soName) {
  var temp;
  var exports = Module.enumerateExports(soName);
  log('---------------- enumerateExports -----------------');
  for (var i = 0; i < exports.length; i++) {
    temp = exports[i];
    log(temp.name + ' ' + temp.type + '  ' + temp.address);
  }
  log('');

  var imports = Module.enumerateImports(soName);
  log('---------------- enumerateImports -----------------');
  for (var i = 0; i < imports.length; i++) {
    temp = imports[i];
    log(temp.name + ' ' + temp.type + '  ' + temp.address);
  }
  log('');

  var symbols = Module.enumerateSymbols(soName);
  log('---------------- enumerateSymbols ------------------');
  for (var i = 0; i < symbols.length; i++) {
    temp = symbols[i];
    log(temp.name + ' ' + temp.type + '  ' + temp.address);
  }
  log('');
}

function dump(pointer, len) {
  var str = hexdump(pointer, {
    offset: 0,
    length: len,
    header: true,
    ansi: true
  });
  log('\n' + str)
}

function logObjectDetail(head, obj) {
  log(' --------- ' + head + '(logObjectDetail) ---------- ' + obj);
  for (var a in obj) {
    log(a)
  }
  log()
}

function buf2hex(buffer) {
  var byteArr = new Uint8Array(buffer);
  var hexArr = [];
  for (var i = 0; i < byteArr.length; i++) {
    var hex = byteArr[i].toString(16);
    var paddedHex = ('00' + hex).slice(-2);
    hexArr.push(paddedHex);
  }
  return hexArr.join(' ');
}

function hexToBuf(hexStr) {
  var hexArr = hexStr.split(' ');
  var byteArr = [];
  for (var i = 0; i < hexArr.length; i++) {
    byteArr.push(parseInt(hexArr[i], 16));
  }
  return new Uint8Array(byteArr).buffer;
}
