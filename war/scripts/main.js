var testMode = 1;
var postListCount = 6;
var bannerElem;
var bannerRotator;

function startUp()
{
  resizeMe();
  getPage(0, 0);
}

function startUpCat(catId)
{
  if(catId == 0)
  {
    startRotator("#home-content-scroller");
		
		$.get( "posts", { sortorder: 1, limit:postListCount, testmode: testMode }).done(function( data ) 
    {
      $(".new-post-list").html(createPostList(data));
    });
		
		$.get( "posts", { sortorder: 0, limit:postListCount, testmode: testMode }).done(function( data ) 
    {
      $(".pop-post-list").html(createPostList(data));
    });
  }
	else
	{
		$.get( "posts", { category: catId, sortorder: 1, limit:postListCount, testmode: testMode }).done(function( data ) 
    {
      $(".new-post-list").html(createPostList(data));
    });
		
		$.get( "posts", { category: catId, sortorder: 2, limit:postListCount, testmode: testMode }).done(function( data ) 
    {
      $(".pop-post-list").html(createPostList(data));
    });
	}
}

function getPage(catId, postId)
{
  if(postId == 0)
  {
    if(catId == 0)
    {
      $(".content-frame").load("home.html", function() { startUpCat(catId); });
    }
    else if(catId == 1)
    {
      $(".content-frame").load("travel/index.html", function() { startUpCat(catId); });
    }
    else if(catId == 2)
    {
      $(".content-frame").load("cooking/index.html", function() { startUpCat(catId); });
    }
    else if(catId == 3)
    {
      $(".content-frame").load("entertainment/index.html", function() { startUpCat(catId); });
    }
    else if(catId == 4)
    {
      $(".content-frame").load("technology/index.html", function() { startUpCat(catId); });
    }
    else if(catId == 5)
    {
      $(".content-frame").load("general/index.html", function() { startUpCat(catId); });
    }
		else
		{
		}
  }
  else
  {
    $.get( "getpage", { category: catId, postId: postId, testmode: testMode }).done(function( data ) 
    {
      $(".content-frame").html(createPost(data));
    });
  }
}

function createPostList(jsonArr)
{
  var params = jQuery.parseJSON(jsonArr);
  var data = "";
	var i = 0;
  
	data = data + '<ul>';
  for(i=0;i<params.data.length;i++)
  {
    data = data + '<li class="postLink" onclick="getPage(' + params.data[i].catId + ',' + params.data[i].postId + ')">' + params.data[i].title + '</li>';
  }
	data = data + '</ul>';
  
  return data;
}

function createPost(jsonArr)
{
  var params = jQuery.parseJSON(jsonArr);
  var data = "";
  
	data = data + '<div class="postDiv">';
	
  if(params.data.length != 0)
  {
    data = data + '<div class="post-title">' + params.data[0].title + '</div>';
    data = data + '<div class="post-path">'  + params.data[0].title + '</div>';
    data = data + '<div class="post-date">'  + params.data[0].title + '</div>';
    data = data + '<div class="post-tags">'  + params.data[0].tags + '</div>';
    data = data + '<div class="post-body">'  + params.data[0].postedAt + '</div>';
    data = data + '</div>';
  }
  else
  {
    data = data + '<div class="post-title"> OOPS! Someone made a boo-boo, bad developer! </div>';
  }
	
	data = data + '</div>';
	data = data + '<div class="postList">';
	data = data + '<div class="bubblingG">';
	data = data + '<span id="bubblingG_1"></span>';
	data = data + '<span id="bubblingG_2"></span>';
	data = data + '<span id="bubblingG_3"></span>';
	data = data + '</div>';
	data = data + '</div>';
	
	$.get( "posts", { category: params.data[0].catId, sortorder: 0, testmode: testMode }).done(function( data ) 
	{
		$(".postList").html(createPostList(data));
	});
  
  return data;
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