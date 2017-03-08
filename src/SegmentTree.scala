import scala.collection.mutable.ArrayBuffer
import scala.io.StdIn

/**
  * Created by zjkgf on 2017/3/7.
  */
class SegmentTree {
  var data: ArrayBuffer[node] = ArrayBuffer[node]()
  var dataSize = 0
  var SegLength = 0
  def buildtree(nownodeposition:Int): Unit ={
    var parent = data(nownodeposition)
    if (parent.y == parent.x) return
    var mid = (parent.y + parent.x) / 2
    var lnode = new node(parent.x,mid,0,true,0,0)
    var rnode = new node(mid+1,parent.y,0,true,0,0)
    var lwz = dataSize
    var rwz = dataSize + 1
    parent.lson = lwz
    parent.rson = rwz
    addnode(lnode)
    addnode(rnode)
    buildtree(lwz)
    buildtree(rwz)
  }
  def addnode(onenode: node): Unit ={
    dataSize += 1
    data += onenode
  }
  def initialize(treesize:Int): Unit ={
    dataSize = 0
    var root = new node(1,treesize,0,true,0,0)
    SegLength = treesize
    addnode(root)
    buildtree(0)
  }
  def clearflag(nownodeposition:Int): Unit ={
    var nownode = data(nownodeposition)
    if (nownode.lson == 0 || nownode.rson == 0){
      if (nownode.lson != 0 || nownode.rson != 0){
        println("ERROR CODE 456")
      }
      nownode.linear_flag = 0
      return
    }
    var nowlnode = data(nownode.lson)
    var nowrnode = data(nownode.rson)
    nowlnode.addlinearflag(nownode.linear_flag)
    nowrnode.addlinearflag(nownode.linear_flag)
    nownode.linear_flag = 0
  }
  def updatenode(nownodeposition:Int): Unit ={
    var nownode = data(nownodeposition)
    if (nownode.lson == 0 || nownode.rson == 0){
      if (nownode.lson != 0 || nownode.rson != 0){
        println("ERROR CODE 456")
      }
      nownode.linear_flag = 0
      return
    }
    var nowlnode = data(nownode.lson)
    var nowrnode = data(nownode.rson)
    nownode.max = Math.max(nowlnode.getmax(),nowrnode.getmax())
    nownode.min = Math.min(nowlnode.getmin(),nowrnode.getmin())
    nownode.sum = nowlnode.getsum() + nowrnode.getsum()
  }
  def addall(x:Int, y:Int, nownodeposition:Int, nownum:Int): Unit ={
    var cx = x
    var cy = y
    if (x > y){
      cx = y
      cy = x
    }
    var nownode = data(nownodeposition)
    var mid = (nownode.x + nownode.y) / 2
    if (cx <=  nownode.x && cy >= nownode.y){
      nownode.addlinearflag(nownum)
      return
    }
    clearflag(nownodeposition)
    if (cx <= mid){
      addall(cx,cy,nownode.lson,nownum)
    }
    if (cy > mid){
      addall(cx,cy,nownode.rson,nownum)
    }
    updatenode(nownodeposition)
  }
  def getinfo(x:Int, y:Int, nownodeposition:Int):ArrayBuffer[Int] = {
    var cx = x
    var cy = y
    if (x > y){
      var result = ArrayBuffer[Int]()
      return result
    }
    var nownode = data(nownodeposition)
    var mid = (nownode.x + nownode.y) / 2
    if (cx <=  nownode.x && cy >= nownode.y){
      var result = ArrayBuffer[Int](nownode.getsum(),nownode.getmax(),nownode.getmin())
      return result
    }
    clearflag(nownodeposition)
    var res = ArrayBuffer[Int]()
    if (cx <= mid){
      var res1 = getinfo(cx,cy,nownode.lson)
      if (res.isEmpty){
        res = res1
      } else if (res.length == 3) {
        res(0) += res1(0)
        res(1) = Math.max(res(1),res1(1))
        res(2) = Math.min(res(2),res1(2))
      } else {
        println("ERROR CODE 455")
      }
    }
    if (cy > mid){
      var res2 = getinfo(cx,cy,nownode.rson)
      if (res.isEmpty){
        res = res2
      } else if (res.length == 3) {
        res(0) += res2(0)
        res(1) = Math.max(res(1),res2(1))
        res(2) = Math.min(res(2),res2(2))
      } else {
        println("ERROR CODE 455")
      }
    }
    res
  }
  def print_arrayinfo(): Unit ={
    for (i <- 1 to SegLength){
      println(getinfo(i,i,0)(0))
    }
  }
  def runscala(): Unit = {
    println("Welcome to Segment Tree Test")
    println("Input Max Segment length: ")
    val num = StdIn.readInt()
    initialize(num)
    while (true) {
      println("Input Command:")
      val ch = StdIn.readChar()
      if (ch == 'G') {
        var x = StdIn.readInt()
        var y = StdIn.readInt()
        println(getinfo(x, y, 0))
      } else if (ch == 'A') {
        var x = StdIn.readInt()
        var y = StdIn.readInt()
        var z = StdIn.readInt()
        addall(x, y, 0, z)
      } else if (ch == 'P') {
        print_arrayinfo()
      } else if (ch == 'Q') return
    }
  }
}