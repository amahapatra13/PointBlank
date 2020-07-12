package com.dscepointblank.pointblank.models

import com.dscepointblank.pointblank.utilityClasses.Constants

data  class UpdateModel(val version :Int = Constants.CURRENT_APK_VERSION, val link :String ="")