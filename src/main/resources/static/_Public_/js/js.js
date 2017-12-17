$(function() {
    //导航
    $('.menu li').mouseover(function() {
        $(this).children('.subMenu').show();
    });
    $('.menu li').mouseout(function() {
        $(this).children('.subMenu').hide();
    });
    $('.subMenu li').mouseover(function() {
        $(this).children('.thirdMenu').show();
    });
    $('.subMenu li').mouseout(function() {
        $(this).children('.thirdMenu').hide();
    });
    // 内容导航条的二级菜单
    $('.conNavBar .navList li').mouseover(function() {

        $(this).children('.navSubMenu').show();
    });
    $('.conNavBar .navList li').mouseout(function() {
        $(this).children('.navSubMenu').hide();
    });
    (function() {
        var lists = $('.bannerList');
        var buttons = $('.bannerBtns span');
        var title = $('.bannerTitle')
        var prev = $('.bannerPrev');
        var next = $('.bannerNext');
        var index = 1;
        var len = 4;
        var interval = 2000;
        var timer;
        // 复制图片
        var last = lists.children().last().prev().html();
        var first = lists.children().first().html();
        lists.prepend(last)
        lists.append(first)
        lists.css('width', (lists.children().length) * parseInt($(document).width()) + 'px')
        lists.css('left', "-"+$(document).width()+"px")
            // 图片的移动
        function animate(offset) {
            var left = parseInt(lists.css('left')) + offset;
            if (offset > 0) {
                offset = '+=' + offset;
            } else {
                offset = '-=' + Math.abs(offset);
            }
            lists.animate({ 'left': offset }, 300, function() {
                if (left >= 0) {
                    lists.css('left', -parseInt($(document).width()) * len);
                }
                if (left < (-parseInt($(document).width()) * len)) {
                    lists.css('left', -parseInt($(document).width()));
                }
            });
        };
        next.bind('click', function() {
            if (lists.is(':animated')) {
                return;
            }
            if (index == 4) {
                index = 1;
            } else {
                index += 1;
            }
            title.html(lists.find('img').eq(index).attr('title'))
            animate(-parseInt($(document).width()));
            showButton();
        });
        prev.bind('click', function() {
            if (lists.is(':animated')) {
                return;
            }
            if (index == 1) {
                index = 4;
            } else {
                index -= 1;
            }
            title.html(lists.find('img').eq(index).attr('title'))
            animate(parseInt($(document).width()));
            showButton()
        });

        function showButton() {
            buttons.eq(index - 1).addClass('active').siblings().removeClass('active');
        };

        buttons.each(function() {
            $(this).bind('click', function() {
                if (lists.is(':animated') || $(this).attr('class') == 'active') {
                    return;
                }
                var myIndex = parseInt($(this).attr('index'));
                var offset = -parseInt($(document).width()) * (myIndex - index);
                animate(offset);
                index = myIndex;
                showButton();
            })
        });

        function play() {
            timer = setTimeout(function() {
                next.trigger('click');
                play();
            }, interval);
        }

        function stop() {
            clearTimeout(timer);
        }
        $('.banner').hover(stop, play);
        play();
    })();



}())
