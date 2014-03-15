var bannerElem;
var bannerRotator;

function startUp()
{
    resizeMe();
	getPage(0, 0);
}

function resizeMe()
{
	//Standard height, for which the body font size is correct
	var preferredHeight = 720; 
	var preferredWidth = 1366; 
	var fontsize = 16;

	var displayHeight = $(window).height();
	var displayWidth = $(window).width();
	var percentageH = ((1.1 * displayHeight) / preferredHeight);
	var percentageW = ((1.1 * displayWidth) / preferredWidth);
	var percentage  = ((percentageH > percentageW)?(percentageW):(percentageH))
	var newFontSize = Math.floor(fontsize * percentage);
	$("body").css("font-size", newFontSize);
}

function getPage(catId, postId)
{
    if(postId == 0)
	{
		if(catId == 0)
		{
		    $(".content-frame").load("home.html", function() { startRotator("#home-content-scroller"); });
		}
		else if(catId == 1)
		{
			$(".content-frame").load("travel/index.html");
		}
		else if(catId == 2)
		{
			$(".content-frame").load("cooking/index.html");
		}
		else if(catId == 3)
		{
			$(".content-frame").load("entertainment/index.html");
		}
		else if(catId == 4)
		{
			$(".content-frame").load("technology/index.html");
		}
	}
	else
	{
		$.get( "getpage", { category: catId, postId: postId }).done(function( data ) 
		{
			$(".content-frame").html(createPost(data));
		});
	}
}

function createPost(jsonArr)
{
    var params = jQuery.parseJSON(jsonArr);
	var data = "";
	
	if(params.data.length != 0)
	{
		data = data + '<div class="post-title">' + params.data[0].title + '</div>';
		data = data + '<div class="post-path">' + '' + '</div>';
		data = data + '<div class="post-date">' + '' + '</div>';
		data = data + '<div class="post-body">' + params.data[0].content + '</div>';
		data = data + '</div>';
	}
	else
	{
		data = data + '<div class="post-title"> OOPS! Someone made a boo-boo, bad developer! </div>';
	}
	
	return data;
}

function rotateBanners(elem, dir) {
  var active = $(elem+" img.active");
  if(dir == 0) {
	var next = active.next();
  }
  else {
	var next = active.prev();
  }
  if (next.length == 0) 
    next = $(elem+" img:first");
  active.removeClass("active").fadeOut(1000);
  next.addClass("active").fadeIn(1000);
}

function nextBanner() {
  clearInterval(bannerRotator);
  rotateBanners(bannerElem, 0);
  bannerRotator = setInterval("rotateBanners('"+bannerElem+"',0)", 6000);
}

function prevBanner() {
  clearInterval(bannerRotator);
  rotateBanners(bannerElem, 1);
  bannerRotator = setInterval("rotateBanners('"+bannerElem+"',0)", 6000);
}
 
function prepareRotator(elem) {
  $(elem+" img").fadeOut(0);
  $(elem+" img:first").fadeIn(0).addClass("active");
}
 
function startRotator(elem) {
  prepareRotator(elem);
  bannerElem = elem;
  bannerRotator = setInterval("rotateBanners('"+elem+"',0)", 6000);
}