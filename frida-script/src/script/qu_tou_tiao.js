Java.perform(function() {
  var Clazz = Java.use("java.lang.Class");
  var Exception = Java.use("java.lang.Exception");
  var String = Java.use("java.lang.String");
  //  var MainActivity = Java.use("com.jifen.qkbase.main.MainActivity");
  var MainActivity = Java.use("com.min.app.sample.MainActivity");
  log("find MainActivity --->", MainActivity)
  log(Process.arch);
})
