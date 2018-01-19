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
     demo:   执行”jstat –gc 1000″ (或 1s)会每隔一秒展示GC监控数据。”jstat –gc 1000 10″会每隔1秒展现一次，且一共10次。
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
        S1C | 输出Survivor1空间的大小。单位KB。  | -gc -gccapacity -gcnew -gcnewcapacity
        S0U | 输出Survivor0已用空间的大小。单位KB。  | -gc -gcnew
        S1U | 输出Survivor1已用空间的大小。单位KB。  | -gc -gcnew
        EC | 输出Eden空间的大小。单位KB。  | -gc -gccapacity -gcnew -gcnewcapacity
        EU | 输出Eden已用空间的大小。单位KB。  | -gc -gcnew
        OC | 输出老年代空间的大小。单位KB  | -gc -gccapacity -gcold -gcoldcapacity
        OU | 输出老年代已用空间的大小。单位KB  | -gc  -gcold
        PC | 输出持久代空间的大小。单位KB  | -gc -gccapacity -gcold -gcoldcapacity -gcpermcapacity
        PU | 输出持久代已用空间的大小。单位KB。  | -gc -gcold
        YGC | 新生代空间GC时间发生的次数   | -gc -gccapacity -gcnew -gcnewcapacity -gcold -gcoldcapacity -gcpermcapacity -gcutil -gccause
        YGCT | 新生代GC处理花费的时间。  | -gc -gcnew -gcutil -gccause
        FGC | full GC发生的次数  | -gc -gccapacity -gcnew -gcnewcapacity -gcold -gcoldcapacity -gcpermcapacity -gcutil -gccause
        FGCT | full GC操作花费的时间  | -gc -gccapacity -gcnew -gcold -gcoldcapacity -gcpermcapacity -gcutil -gccause
        GCT | GC操作花费的总时间  | -gc -gccapacity -gcnew -gcold -gcoldcapacity -gcpermcapacity -gcutil -gccause
        NGCMN | 新生代最小空间容量，单位KB。  | -gccapacity -gcnewcapacity
        NGCMX | 新生代最大空间容量，单位KB  | -gccapacity -gcnewcapacity
        NGC | 新生代当前空间容量，单位KB  | -gccapacity -gcnewcapacity
        OGCMN |  老年代最小空间容量，单位KB。 | -gccapacity -gcoldcapacity
        OGCMX |  老年代最大空间容量，单位KB | -gccapacity -gcoldcapacity
        OGC | 老年代当前空间容量制，单位KB。  | -gccapacity -gcoldcapacity
        PGCMN |  持久代最小空间容量，单位KB | -gccapacity -gcpermcapacity
        PGCMX |  持久代最大空间容量，单位KB。 | -gccapacity -gcpermcapacity
        PGC|  持久代当前空间容量，单位KB。 | -gccapacity -gcpermcapacity
        PC |  持久代当前空间大小，单位KB | -gccapacity -gcpermcapacity
        PU |  持久代当前已用空间大小，单位KB | -gccapacity -gcold
        LGCC |  最后一次GC发生的原因 | -gccause
        GCC |  当前GC发生的原因 | -gccause
        TT |  老年化阈值。被移动到老年代之前，在新生代空存活的次数。 | -gcnew
        MTT |  最大老年化阈值。被移动到老年代之前，在新生代空存活的次数 | -gcnew
        DSS |  幸存者区所需空间大小，单位KB | -gcnew


### -verbosegc 参数
        下面是-verbosegc 的可用参数
        · -XX:+PrintGCDetails
        · -XX:+PrintGCTimeStamps
        · -XX:+PrintHeapAtGC
        · -XX:+PrintGCDateStamps (from JDK 6 update 4)

        如果只是用了 -verbosegc 。那么默认会加上 -XX:+PrintGCDetails。 –verbosgc 的附加参数并不是独立的。而是经常组合起来使用。
        使用 –verbosegc后，每次GC发生你都会看到如下格式的结果。

        [GC [<collector>: <starting occupancy1> -> <ending occupancy1>, <pause time1> secs] <starting occupancy3> -> <ending occupancy3>, <pause time3> secs]

        收集器	|   minor gc使用的收集器的名字。
        starting occupancy1 |	GC执行前新生代空间大小。
        ending occupancy1 |	GC执行后新生代空间大小。
        pause time1 |	因为执行minor GC，Java应用暂停的时间。
        starting occupancy3 |	GC执行前堆区域总大小
        ending occupancy3 |	GC执行后堆区域总大小
        pause time3	|  Java应用由于执行堆空间GC（包括major GC）而停止的时间。


###   (Java) VisualVM  + Visual GC

###    HPJMeter






### GC 的目的
    一个是将转移到老年代的对象数量降到最少
    另一个是减少Full GC的执行时间


### gc 启动参数
    分类  参数  备考

    Serial GC  -XX:+UseSerialGC

    Parallel GC  -XX:+UseParallelGC   -XX:ParallelGCThreads=value

    Parallel Compacting GC  -XX:+UseParallelOldGC

    CMS GC       -XX:+UseConcMarkSweepGC
                 -XX:+UseParNewGC
                 -XX:+CMSParallelRemarkEnabled
                 -XX:CMSInitiatingOccupancyFraction=value
                 -XX:+UseCMSInitiatingOccupancyOnly

    G1  -XX:+UnlockExperimentalVMOptions  -XX:+UseG1GC    在JDK6中这两个参数必须同时使用

### 内存时出现OutOfMemoryError ，你应该执行堆内存转储（heap dump）
        jmap -histo 3383 输出下列四列

        num   intances   bytes   className



        jmap -dump:format=b,file=result3834 然后用eclipse 或者专业的工具进行分析

        jmap [ option ] pid

        jmap [ option ] executable core

        jmap [ option ] [server-id@]remote-hostname-or-IP

     jmap –heap 3772

        using parallel threads in the new generation.  ##新生代采用的是并行线程处理方式

        using thread-local object allocation.

        Concurrent Mark-Sweep GC   ##同步并行垃圾回收

        Heap Configuration:  ##堆配置情况

        MinHeapFreeRatio = 40 ##最小堆使用比例

        MaxHeapFreeRatio = 70 ##最大堆可用比例

        MaxHeapSize      = 2147483648 (2048.0MB) ##最大堆空间大小

        NewSize          = 268435456 (256.0MB) ##新生代分配大小

        MaxNewSize       = 268435456 (256.0MB) ##最大可新生代分配大小

        OldSize          = 5439488 (5.1875MB) ##老生代大小

        NewRatio         = 2  ##新生代比例

        SurvivorRatio    = 8 ##新生代与suvivor的比例

        PermSize         = 134217728 (128.0MB) ##perm区大小

        MaxPermSize      = 134217728 (128.0MB) ##最大可分配perm区大小



        Heap Usage: ##堆使用情况

        New Generation (Eden + 1 Survivor Space):  ##新生代（伊甸区 + survior空间）

        capacity = 241631232 (230.4375MB)  ##伊甸区容量

        used     = 77776272 (74.17323303222656MB) ##已经使用大小

        free     = 163854960 (156.26426696777344MB) ##剩余容量

        32.188004570534986% used ##使用比例

        Eden Space:  ##伊甸区

        capacity = 214827008 (204.875MB) ##伊甸区容量

        used     = 74442288 (70.99369812011719MB) ##伊甸区使用

        free     = 140384720 (133.8813018798828MB) ##伊甸区当前剩余容量

        34.65220164496263% used ##伊甸区使用情况

        From Space: ##survior1区

        capacity = 26804224 (25.5625MB) ##survior1区容量

        used     = 3333984 (3.179534912109375MB) ##surviror1区已使用情况

        free     = 23470240 (22.382965087890625MB) ##surviror1区剩余容量

        12.43827838477995% used ##survior1区使用比例

        To Space: ##survior2 区

        capacity = 26804224 (25.5625MB) ##survior2区容量

        used     = 0 (0.0MB) ##survior2区已使用情况

        free     = 26804224 (25.5625MB) ##survior2区剩余容量

        0.0% used ## survior2区使用比例

        concurrent mark-sweep generation: ##老生代使用情况

        capacity = 1879048192 (1792.0MB) ##老生代容量

        used     = 30847928 (29.41887664794922MB) ##老生代已使用容量

        free     = 1848200264 (1762.5811233520508MB) ##老生代剩余容量

        1.6416783843721663% used ##老生代使用比例

        Perm Generation: ##perm区使用情况

        capacity = 134217728 (128.0MB) ##perm区容量

        used     = 47303016 (45.111671447753906MB) ##perm区已使用容量

        free     = 86914712 (82.8883285522461MB) ##perm区剩余容量

        35.24349331855774% used ##perm区使用比例




 #### demo

    $ jstat -gcutil 21719 1s
    S0    S1    E    O    P    YGC    YGCT    FGC    FGCT GCT
    48.66 0.00 48.10 49.70 77.45 3428 172.623 3 59.050 231.673
    48.66 0.00 48.10 49.70 77.45 3428 172.623 3 59.050 231.673
    如上表，我们先看一下YGC 和YGCT，计算YGCT/ YGC得到0.050秒（50毫秒）。这意味着新生代空间上的GC操作平均花费50毫秒。在这种情况，你大可不必担心新生代空间上执行的GC操作。
    接下来，我们来看一下FGCT 和FGC。，计算FGCT/ FGC得到19.68秒，这意味着GC的平均执行时间为19.68秒，可能是每次花费19.68秒执行了三次，也可能是其中的两次执行了1秒而另一次执行了58秒。不论哪种情况，都需要进行GC优化。

    通过jstat 命令可以很轻易地查看GC状态，但是，分析GC的最佳方式是通过–verbosegc参数来生成日志，在之前的文章中我已经解释了如何分析这些日志，HPJMeter 是我个人最喜欢的用于分析-verbosegc 日志的工具。他很易于使用和分析结果。通过HPJmeter你可以很轻易查看GC执行时间以及GC发生频率。如果GC执行时间满足下面所有的条件，就意味着无需进行GC优化了。

    Minor GC执行的很快（小于50ms）
    Minor GC执行的并不频繁（大概10秒一次）
    Full GC执行的很快（小于1s）
    Full GC执行的并不频繁（10分钟一次）