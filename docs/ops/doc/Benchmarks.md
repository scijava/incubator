# SciJava Ops Benchmarks

<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/dygraphs@2.1.0/dist/dygraph.min.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/dygraphs@2.1.0/dist/dygraph.min.css" />

## Cheap operation results

<style type="text/css">
  .dygraph {
    display: inline-block;
    max-width: 100%;
    width: 435px;
    height: 250px;
  }
  .dygraph-legend {
    background-color: rgba(200, 200, 255, 0.75) !important;
    padding: 4px;
    border: 1px solid #000;
    border-radius: 10px;
    box-shadow: 4px 4px 4px #888;
    pointer-events: none;
    width: 12em;
  }
  .dygraph-legend > span.highlight { background-color: rgba(255, 255, 200, 0.75) !important; }
  .dygraph-legend > span.highlight { display: inline; }
</style>

<div>
<div class="dygraph" id="cheapIterationVsTime1"></div>
<div class="dygraph" id="cheapIterationVsTime25"></div>
</div>

<div>
<div class="dygraph" id="cheapResolutionVsTime1"></div>
<div class="dygraph" id="cheapResolutionVsTime10"></div>
</div>

## Expensive operation results

<div>
<div class="dygraph" id="expensiveIterationVsTime1"></div>
<div class="dygraph" id="expensiveIterationVsTime25"></div>
</div>

<div>
<div class="dygraph" id="expensiveResolutionVsTime1"></div>
<div class="dygraph" id="expensiveResolutionVsTime10"></div>
</div>

<script type="text/javascript">
  function plot(id, title, xlabel, data) {
    new Dygraph(document.getElementById(id), data, {
      title: title,
      titleHeight: 24,
      xlabel: xlabel,
      ylabel: "Time",
      includeZero: true,
      labelsSeparateLines: true,
      drawPoints: true,
      pointSize: 3,
      highlightCircleSize: 2,
      strokeWidth: 1,
      strokeBorderWidth: 1,
      highlightSeriesOpts: {
        strokeWidth: 3,
        strokeBorderWidth: 1,
        highlightCircleSize: 5
      }
    });
  }
  plot("cheapIterationVsTime1", "Iteration x Time (ms) at 1 Mpx", "Iteration",
    "Iteration,ImageJ,ImgLib2 Array,ImgLib2 Cell,ImgLib2 ImagePlus,ImgLib2 Planar,Raw\n" +
    "1,9,15,15,12,12,4\n" +
    "2,5,5,9,8,5,0\n" +
    "3,0,0,2,1,0,0\n" +
    "4,0,0,3,0,0,0\n" +
    "5,0,0,3,0,0,0\n" +
    "6,0,0,3,0,0,0\n" +
    "7,0,0,3,0,0,0\n" +
    "8,0,0,2,0,1,0\n" +
    "9,0,0,3,0,0,0\n" +
    "10,0,0,2,0,0,1");

  plot("cheapIterationVsTime25", "Iteration x Time (ms) at 25 Mpx", "Iteration",
    "Iteration,ImageJ,ImgLib2 Array,ImgLib2 Cell,ImgLib2 ImagePlus,ImgLib2 Planar,Raw\n" +
    "1,10,42,92,39,40,7\n" +
    "2,5,35,78,33,30,5\n" +
    "3,2,2,79,2,3,3\n" +
    "4,2,2,78,2,3,3\n" +
    "5,2,2,78,2,2,3\n" +
    "6,1,2,79,3,3,3\n" +
    "7,2,1,75,2,2,4\n" +
    "8,1,2,79,2,3,3\n" +
    "9,2,2,78,4,2,3\n" +
    "10,1,3,83,3,3,4");

  plot("cheapResolutionVsTime1", "Resolution x Time (ms) at iteration #1", "Mpx",
    "Mpx,ImageJ,ImgLib2 Array,ImgLib2 Cell,ImgLib2 ImagePlus,ImgLib2 Planar,Raw\n" +
    "1 Mpx,9,15,15,12,12,4\n" +
    "4 Mpx,8,16,25,14,15,5\n" +
    "7 Mpx,9,18,36,17,17,5\n" +
    "10 Mpx,9,25,38,23,21,5\n" +
    "13 Mpx,9,28,49,24,23,6\n" +
    "16 Mpx,9,31,60,28,27,6\n" +
    "19 Mpx,9,36,66,31,31,6\n" +
    "22 Mpx,10,37,74,35,33,7\n" +
    "25 Mpx,10,42,92,39,40,7");

  plot("cheapResolutionVsTime10", "Resolution x Time (ms) at iteration #10", "Mpx",
    "Mpx,ImageJ,ImgLib2 Array,ImgLib2 Cell,ImgLib2 ImagePlus,ImgLib2 Planar,Raw\n" +
    "1 Mpx,0,0,2,0,0,1\n" +
    "4 Mpx,0,0,15,1,0,1\n" +
    "7 Mpx,1,0,21,2,0,0\n" +
    "10 Mpx,1,0,30,1,1,1\n" +
    "13 Mpx,1,1,39,1,2,1\n" +
    "16 Mpx,1,1,52,1,2,2\n" +
    "19 Mpx,1,1,65,1,2,2\n" +
    "22 Mpx,2,1,70,2,2,2\n" +
    "25 Mpx,1,3,83,3,3,4");

  plot("expensiveIterationVsTime1", "Iteration x Time (ms) at 1 Mpx", "Iteration",
    "Iteration,ImageJ,ImgLib2 Array,ImgLib2 Cell,ImgLib2 ImagePlus,ImgLib2 Planar,Raw\n" +
    "1,61,61,68,58,56,58\n" +
    "2,43,44,55,52,49,50\n" +
    "3,39,41,50,43,43,41\n" +
    "4,42,44,47,42,39,40\n" +
    "5,40,43,41,39,41,43\n" +
    "6,38,43,44,43,44,40\n" +
    "7,40,44,41,42,41,41\n" +
    "8,39,38,40,42,40,41\n" +
    "9,45,39,43,43,47,43\n" +
    "10,38,43,47,43,46,40");

  plot("expensiveIterationVsTime25", "Iteration x Time (ms) at 25 Mpx", "Iteration",
    "Iteration,ImageJ,ImgLib2 Array,ImgLib2 Cell,ImgLib2 ImagePlus,ImgLib2 Planar,Raw\n" +
    "1,1161,1171,1332,1126,1162,1277\n" +
    "2,1052,1062,1210,1066,1069,1024\n" +
    "3,1037,1044,1174,1030,1033,1000\n" +
    "4,1008,1038,1137,1033,1026,978\n" +
    "5,1027,1041,1137,1037,1037,997\n" +
    "6,1017,1034,1096,1031,1058,984\n" +
    "7,978,1049,1079,1060,1042,983\n" +
    "8,999,1040,1072,1046,1030,989\n" +
    "9,993,1071,1100,1047,1042,983\n" +
    "10,989,1037,1061,1030,1041,973");

  plot("expensiveResolutionVsTime1", "Resolution x Time (ms) at iteration #1", "Mpx",
    "Mpx,ImageJ,ImgLib2 Array,ImgLib2 Cell,ImgLib2 ImagePlus,ImgLib2 Planar,Raw\n" +
    "1 Mpx,61,61,68,58,56,58\n" +
    "4 Mpx,185,196,232,202,190,222\n" +
    "7 Mpx,323,338,379,336,324,365\n" +
    "10 Mpx,477,485,595,457,458,508\n" +
    "13 Mpx,608,615,692,601,614,667\n" +
    "16 Mpx,747,766,944,723,750,831\n" +
    "19 Mpx,871,884,1019,849,878,1075\n" +
    "22 Mpx,1013,1045,1170,973,1013,1090\n" +
    "25 Mpx,1161,1171,1332,1126,1162,1277");

  plot("expensiveResolutionVsTime10", "Resolution x Time (ms) at iteration #10", "Mpx",
    "Mpx,ImageJ,ImgLib2 Array,ImgLib2 Cell,ImgLib2 ImagePlus,ImgLib2 Planar,Raw\n" +
    "1 Mpx,38,43,47,43,46,40\n" +
    "4 Mpx,165,169,173,157,180,167\n" +
    "7 Mpx,286,292,298,284,299,279\n" +
    "10 Mpx,390,435,423,412,420,397\n" +
    "13 Mpx,518,589,529,535,547,512\n" +
    "16 Mpx,646,655,668,652,661,636\n" +
    "19 Mpx,764,779,799,775,774,745\n" +
    "22 Mpx,866,913,922,911,909,859\n" +
    "25 Mpx,989,1037,1061,1030,1041,973");
</script>
