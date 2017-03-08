/**
  * Created by zjkgf on 2017/3/8.
  */
class node(ini_x: Int, ini_y: Int, ini_sum: Int, ini_exist: Boolean, ini_lson: Int, ini_rson: Int) {
  var x: Int = ini_x
  var y: Int = ini_y
  var sum: Int = ini_sum
  var min: Int = 0
  var max: Int = 0
  var linear_flag: Int = 0
  var existed: Boolean = ini_exist
  var lson: Int = ini_lson
  var rson: Int = ini_rson
  def addlinearflag(parentflag: Int){
    var total = y-x+1
    min = min + parentflag
    max = max + parentflag
    sum = sum + parentflag * total
    linear_flag += parentflag
  }
  def getsum():Int={
    sum
  }
  def getmin():Int={
    min
  }
  def getmax():Int={
    max
  }
  def getlinearflag():Int={
    linear_flag
  }
  def getlson():Int={
    lson
  }
  def getrson():Int={
    rson
  }
  def clearflag(){
    linear_flag = 0
  }
}
