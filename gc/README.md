### stop-the-world
  stop-the-world可以发生在任何gc算法中
  stop-the-world会暂停app运行
  当Stop-the-world发生时，除了GC所需的线程以外，所有线程都处于等待状态，直到GC任务完成。GC优化很多时候就是指减少Stop-the-world发生的时间。

### 老年代GC
      Serial GC
      Parallel GC
      Parallel Old GC (Parallel Compacting GC)
      Concurrent Mark & Swepp GC (CMS)
      Garbage First (G1) GC

### Serial Gc(-XX:+UseSerialGC)
        第一步是标记老年代中依然存活对象。（标记）
        第二步，从头开始检查堆内存空间，并且只留下依然幸存的对象。（清理）
        最后一步，从头开始，顺序地填满堆内存空间，并且将对内存空间分成两部分：一个保存着对象，另一个空着（压缩）。

### Parallel GC (-XX:+UseParallelGC)
    多线程执行 Serial GC 的步骤

### Parallel Old GC(-XX:+UseParallelOldGC)
        Parallel Old GC在JDK5之后出现。与parallel GC相比，唯一的区别在于针对老年代的GC算法。Parallel Old GC分为三步：
        标记-汇总-压缩（mark – summary – compaction）。汇总（summary）步骤与清理（sweep）的不同之处在于，其将依然幸存
        的对象分发到GC预先处理好的不同区域，算法相对清理来说略微复杂一点。

### CMS GC (-XX:+UseConcMarkSweepGC)
        CMS GC比我之前解释的各种算法都要复杂很多。第一步初始化标记（initial mark） 比较简单。这一步
        骤只是查找那些距离类加载器最近的幸存对象。因此，停顿的时间非常短暂。在之后的并行标记（ concurrent mark ）步骤，所有
        被幸存对象引用的对象会被确认是否已经被追踪和校验。这一步的不同之处在于，在标记的过程中，其他的线程依然在执行。在重新标记
        （remark）步骤，会再次检查那些在并行标记步骤中增加或者删除的与幸存对象引用的对象。最后，在并行交换（ concurrent sweep ）
        步骤，转交垃圾回收过程处理。垃圾回收工作会在其他线程的执行过程中展开。一旦采取了这种GC类型，由GC导致的暂停时间会极其短暂。
        CMS GC也被称为低延迟GC。它经常被用在那些对于响应时间要求十分苛刻的应用之上。

        当然，这种GC类型在拥有stop-the-world时间很短的优点的同时，也有如下缺点：

        它会比其他GC类型占用更多的内存和CPU
        默认情况下不支持压缩步骤
        在使用这个GC类型之前你需要慎重考虑。如果因为内存碎片过多而导致压缩任务不得不执行，那么stop-the-world的时间要比其他任何GC类
        型都长，你需要考虑压缩任务的发生频率以及执行时间。

###  G1 GC

     目前还不算稳定,略过


### 监控工具 jstat
     参数介绍

        gc

        输出每个堆区域的当前可用空间以及已用空间，GC执行的总次数，GC操作累计所花费的时间。

        gccapacity

        输出每个堆区域的最小空间限制（ms）/最大空间限制（mx），当前大小，每个区域之上执行GC的次数。（不输出当前已用空间以及GC执行时间）。

        gccause

        输出-gcutil提供的信息以及最后一次执行GC的发生原因和当前所执行的GC的发生原因

        gcnew

        输出新生代空间的GC性能数据

        gcnewcapacity

        输出新生代空间的大小的统计数据。

        gcold

        输出老年代空间的GC性能数据。

        gcoldcapacity

        输出老年代空间的大小的统计数据。

        gcpermcapacity

        输出持久带空间的大小的统计数据。

        gcutil

        输出每个堆区域使用占比，以及GC执行的总次数和GC操作所花费的时间。

     输出结果项说明

        列 | 说明 | Jstat参数
        ----|------|----
        S0C | 输出Survivor0空间的大小。单位KB。  | -gc -gccapacity -gcnew -gcnewcapacity
        ua | 服务端  | 是
        ip | 服务端  | 是
        clt | 客户端  | 是
        pf | 客户端  | 是
        typ | 客户端  | 是
        tpc | 客户端  | 是
        pid | 客户端  | 是
        ppid | 客户端  | 是
        eid | 客户端  | 是