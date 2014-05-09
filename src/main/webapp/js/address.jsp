<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script LANGUAGE="JavaScript">
function Dsy()
{
    this.Items = {};
}
Dsy.prototype.add = function(id,iArray)
{
    this.Items[id] = iArray;
}
Dsy.prototype.Exists = function(id)
{
    if(typeof(this.Items[id]) == "undefined") return false;
    return true;
}
 
function change(v){
    var str="0";
    for(i=0;i<v ;i++){ str+=("_"+(document.getElementById(s[i]).selectedIndex-1));};
    var ss=document.getElementById(s[v]);
    with(ss){
        length = 0;
        options[0]=new Option(opt0[v],opt0[v]);
        if(v && document.getElementById(s[v-1]).selectedIndex>0 || !v)
        {
            if(dsy.Exists(str)){
                ar = dsy.Items[str];
                for(i=0;i<ar .length;i++)options[length]=new Option(ar[i],ar[i]);
                if(v)options[1].selected = true;
            }
        }
        if(++v<s.length){change(v);}
    }
}
 
var dsy = new Dsy();
dsy.add("0",["女生宿舍","男生宿舍","梅岭村","通泰","荷花村","办公地点","其他"]);
dsy.add("0_0",["新一舍","12舍","14舍"]);
dsy.add("0_1",["1舍","2舍","3舍","4舍","5舍","10舍","11舍"]);
dsy.add("0_2",["1栋","2栋","3栋","4栋","5栋","6栋","7栋","8栋","9栋","10栋","11栋","12栋","13栋","14栋","15栋","16栋","17栋","18栋","19栋",
               "20栋","21栋","22栋","23栋","24栋","25栋","26栋","27栋","28栋","29栋","30栋","31栋","32栋","33栋","34栋","35栋","36栋","37栋","38栋",
               "39栋","40栋","41栋","42栋","43栋","44栋","45栋"]);
dsy.add("0_3",["1栋","2栋","3栋","4栋","5栋","6栋","7栋","8栋","9栋","10栋","11栋","12栋","13栋","14栋"]);
dsy.add("0_4",["1栋","2栋","3栋","4栋","5栋","6栋","7栋","8栋","9栋","10栋","11栋","12栋","13栋","14栋"]); 
dsy.add("0_5",["世纪楼","办公楼","图书馆","创业楼北","创业楼南","红楼","一综合实验楼","二综合实验楼","建工楼","青工楼","电子楼","机械楼","火灾实验室","其他"]);
dsy.add("0_6",["幼儿园"]); 
var s=["s1","s2"];
var opt0 = ["请选择","请选择"];
function setup()
{
    for(i=0;i<s .length-1;i++)
        document.getElementById(s[i]).onchange=new Function("change("+(i+1)+")");
    change(0);
}
</script>