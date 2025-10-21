// nav animation
$(function() {

    var initFunc = function(){
        var windowH = $(window).height();
        var windowW = $(window).width();

        // input text
        var inputContW = $(".userInputCont .inputTypeCont").width();
        var inputW = inputContW - 5 -50 -35;
        $(".userInputCont .inputTypeCont input.commonInput").css({"width":inputW+"px"});

        // input select
        var inputselectW = inputContW - 5 -50 -25;
        $(".userInputCont .inputTypeCont select").css({"width":inputW+"px"});

        // inputFunc
        var comInputFuncW = inputW - 50 - 10 - 15;
        $(".inputTypeCont .commonInputFunc").css({"width":comInputFuncW+"px"});
    };

    initFunc();

    $(window).resize(function(){
        initFunc();
    });

    $(".headerNavIcon").click(function(){
        $(this).toggleClass("headerNavIconClick headerNavIconOut");

        // modify headerNavIcon span color
        if ($(".headerNavCont").css('display') != 'none' ){
            $(".headerNavIcon span").css({"background":"#333333"});
        } else{
            $(".headerNavIcon span").css({"background":"#ffffff"});
        }

        $(".headerNavCont").slideToggle(250);

    });

    $(".headerNavCont a").each(function( index ) {
        $( this ).css({'animation-delay': (index/10)+'s'});
    });

    // 设置导航跳转连接
    function setHeaderNav() {
        var baseUrl = window.location.origin;

        // 如果您的项目有部署名（例如：/express），请使用下面这行代码代替上面那行：
        // var baseUrl = window.location.origin + "/express";

        var headNav = $(".headerNavCont a");

        var navArray = [
            "/index.html",
            "/wxUserhome.html",
            "#",
            "/addExpress.html",
            "/lazyboard.html",
            "/expressAssist.html"
        ];

        for (var i = 0; i < headNav.length; i++) {
            headNav[i].href = baseUrl + navArray[i];

            if(i == 2 || i == 3 || i == 4){
                headNav[i].style.display = "none";
            }
            if(i == 5) {
                headNav[i].style.display = "none";
            }
        }
    }
    setHeaderNav();

});