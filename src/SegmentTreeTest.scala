import scala.util.Random

/**
  * Created by zjkgf on 2017/3/8.
  */
class SegmentTreeTest {
  def runTest(): Unit = {
    var tree = new SegmentTree
    val maxnumber = 20
    val maxcommand = 500000
    tree.initialize(maxnumber)
    var samearray = new Array[Int](maxnumber)
    Random.setSeed(3)
    for (i <- 1 to maxcommand){
      var x = Random.nextInt(maxnumber)
      var y = Random.nextInt(maxnumber)
      if (x>y){
        var p = x
        x = y
        y = p
      }
      x += 1
      y += 1
      var gamblecommand = Random.nextInt(2)
      if (gamblecommand == 0){
        var minn,maxn = -111
        var first = 0
        var sumn = 0
        for (j <- x-1 to y-1){
          if (first == 0) {
            minn = samearray(j)
            maxn = samearray(j)
            first = 1
          }
          minn = Math.min(minn,samearray(j))
          maxn = Math.max(maxn,samearray(j))
          sumn += samearray(j)
        }
        //println("G "+x+" "+y)
        var res = tree.getinfo(x,y,0)
        if (res(0) == sumn && res(1) == maxn && res(2) == minn){
          //println("Pass "+i)
        } else{
          println("Unpass "+i+" "+res+sumn+" "+maxn+" "+minn)
          return
        }
      } else if (gamblecommand == 1){
        var num = Random.nextInt(20)-10
        for (j <- x-1 to y-1){
          samearray(j) += num
        }
        tree.addall(x,y,0,num)
        //println("A "+x+" "+y+" "+num)
      }
    }
    println("Have Passed All")
  }
}
