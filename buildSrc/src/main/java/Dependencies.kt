object Versions {
  val android_plugin_version = "3.1.1"
  val android_groovy_plugin_version = "2.0.0"

  val kotlin_version = "1.2.30"

  val dagger_version = "2.14.1"
  val retro_fit_version = "2.2.0"
  val rx_java_version = "1.2.6"
  val rx_android_version = "1.1.0"
  val gson_version = "2.8.2"


  val constraint_layout_version = "1.0.2"
  val app_compat_version = "26.1.0"

  val ok_http_version = "3.7.0"

  val build_tools_version = "26.0.2"
  val compile_sdk_version = 26
  val target_sdk_version = 26
  val min_sdk_version = 21

  val annotation_processor_version = "10.0-b28"

  val picasso_version = "2.5.2"

  // Test versions
  val groovy_version = "2.4.1"
  val spock_version = "1.0-groovy-2.4"
  val junit_version = "4.12"
  val slf4j_api_version = "1.7.10"
  val objenesis_version = "2.1"
  val cglib_nodep_version = "3.1"

  val support_test_runner_version = "1.0.2"
  val espresso_version = "3.0.2"

}

object Plugins {

  val android = "com.android.tools.build:gradle:${Versions.android_plugin_version}"
  val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin_version}"
  val groovyAndroid = "org.codehaus.groovy:groovy-android-gradle-plugin:${Versions.android_groovy_plugin_version}"
}

object AnnotationProcessors {
  val javaX = "javax.inject:javax.inject:1"
  val databinding = "com.android.databinding:compiler:${Versions.android_plugin_version}"
}

object Libs {

  val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jre7:${Versions.kotlin_version}"
  val retroFitGson = "com.squareup.retrofit2:converter-gson:${Versions.retro_fit_version}"

  val rx = "io.reactivex:rxjava:${Versions.rx_java_version}"
  val rxAndroid = "io.reactivex:rxandroid:${Versions.rx_android_version}"

  val daggerCore = "com.google.dagger:dagger:${Versions.dagger_version}"
  val daggerAndroid = "com.google.dagger:dagger-android:${Versions.dagger_version}"
  val daggerAndroidSupport = "com.google.dagger:dagger-android-support:${Versions.dagger_version}"
  val dagger = arrayOf(daggerCore,daggerAndroid, daggerAndroidSupport)

  val appCompatV7 = "com.android.support:appcompat-v7:${Versions.app_compat_version}"
  val materialDesignSupport = "com.android.support:design:${Versions.app_compat_version}"
  val recyclerView = "com.android.support:recyclerview-v7:${Versions.app_compat_version}"
  val constraintLayout = "com.android.support.constraint:constraint-layout:${Versions.constraint_layout_version}"
  val cardView = "com.android.support:cardview-v7:${Versions.app_compat_version}"
  val exifinterface = "com.android.support:exifinterface:${Versions.app_compat_version}"


  val androidSupport = arrayOf(appCompatV7, materialDesignSupport, recyclerView)

  val gson = "com.google.code.gson:gson:${Versions.gson_version}"


  val picasso = "com.squareup.picasso:picasso:${Versions.picasso_version}"

}

object TestLibs {
  val junit = "junit:junit:${Versions.junit_version}"
  //groovy stuff
  val slf4j = "org.slf4j:slf4j-api:${Versions.slf4j_api_version}"
  val objenesis = "org.objenesis:objenesis:${Versions.objenesis_version}"
  val groovy = "org.codehaus.groovy:groovy-all:${Versions.groovy_version}"
  val spockCore = "org.spockframework:spock-core:${Versions.spock_version}"
  val cgLib = "cglib:cglib-nodep:${Versions.cglib_nodep_version}"

  val spockLibs = arrayOf(slf4j, objenesis, groovy, spockCore, cgLib)

  val androidTestRunner = "com.android.support.test:runner:${Versions.support_test_runner_version}"
  val espresso = "com.android.support.test.espresso:espresso-core:${Versions.espresso_version}"
}


