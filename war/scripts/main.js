var testMode = 0;
var postListCount = 6;
var bannerElem;
var bannerRotator;
var catNames   = new Array("Home", "Travel", "Cooking", "Technology", "Entertainment", "General");
var monthNames = new Array("JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC");
var catLinks   = new Array("/", "/travel/", "/cooking/", "/tech/", "/ent/", "/gen/");

function startUp()
{
  resizeMe();
	
	if((window.location.pathname != null) && (window.location.pathname != "/"))
  {
    getPath(window.location.pathname, false);
  }
  else
  {
    getPage(0, 0, false);
  }
	
	window.addEventListener("popstate", function(e) 
  {
    if(e.state == null)
    {
      getPage(0, 0, false);
    }
    else
    {
		  if(e.state["link"] == null)
      {
        getPage(e.state["catId"], e.state["postId"], false);
		  }
			else
			{
			  getPath(e.state["link"], false);
			}
    }
  });
}

function startUpCat(catId)
{
  testMode = getParameterByName("testMode");
	
  if(catId == 0)
  {
    startRotator("#home-content-scroller");
		
		$.get( "/posts", { sortorder: 1, limit:postListCount, testmode: testMode }).done(function( data ) 
    {
      $(".new-post-list").html(createPostList(data));
    });
		
		$.get( "/posts", { sortorder: 0, limit:postListCount, testmode: testMode }).done(function( data ) 
    {
      $(".pop-post-list").html(createPostList(data));
    });
  }
	else
	{
		$.get( "/posts", { category: catId, sortorder: 1, limit:postListCount, testmode: testMode }).done(function( data ) 
    {
      $(".new-post-list").html(createPostList(data));
    });
		
		$.get( "/posts", { category: catId, sortorder: 2, limit:postListCount, testmode: testMode }).done(function( data ) 
    {
      $(".pop-post-list").html(createPostList(data));
    });
	}
}

function getPath(path, addHistory)
{
  addHistory   = (typeof addHistory === "undefined") ? true : addHistory;
  testMode     = getParameterByName("testMode");
	
	uriParts     = path.split('/');
	var catId    = catLinks.indexOf("/"+ uriParts[1]+"/");
	var link     = catLinks[catId];
	var stateObj = { catId: catId, link: path };
	
  if(uriParts[2] == "")
  {
    if(catId == 0)
    {
      $(".content-frame").load("/home.html", function() { startUpCat(catId); });
    }
    else if(catId == 1)
    {
      $(".content-frame").load("/travel/data.html", function() { startUpCat(catId); });
    }
    else if(catId == 2)
    {
      $(".content-frame").load("/cooking/data.html", function() { startUpCat(catId); });
    }
    else if(catId == 3)
    {
      $(".content-frame").load("/technology/data.html", function() { startUpCat(catId); });
    }
    else if(catId == 4)
    {
      $(".content-frame").load("/entertainment/data.html", function() { startUpCat(catId); });
    }
    else if(catId == 5)
    {
      $(".content-frame").load("/general/data.html", function() { startUpCat(catId); });
    }
		else
		{
		}

		if(addHistory == true)
		{
		  history.pushState(stateObj, catNames[catId], link);
	  }
  }
  else
  {
    $.get( "/getpage", { category: catId, link: uriParts[2], testmode: testMode }).done(function( data ) 
    {
      $(".content-frame").html(createPost(data, stateObj, link, addHistory));
			formatCodeBlock();
    });
  }
}

function getPage(catId, postId, addHistory)
{
  addHistory = (typeof addHistory === "undefined") ? true : addHistory;
  testMode = getParameterByName("testMode");
	
	var link = catLinks[catId];
	var stateObj = { catId: catId, postId: postId };
	
  if(postId == 0)
  {
    if(catId == 0)
    {
      $(".content-frame").load("/home.html", function() { startUpCat(catId); });
    }
    else if(catId == 1)
    {
      $(".content-frame").load("/travel/data.html", function() { startUpCat(catId); });
    }
    else if(catId == 2)
    {
      $(".content-frame").load("/cooking/data.html", function() { startUpCat(catId); });
    }
    else if(catId == 3)
    {
      $(".content-frame").load("/technology/data.html", function() { startUpCat(catId); });
    }
    else if(catId == 4)
    {
      $(".content-frame").load("/entertainment/data.html", function() { startUpCat(catId); });
    }
    else if(catId == 5)
    {
      $(".content-frame").load("/general/data.html", function() { startUpCat(catId); });
    }
		else
		{
		}

		if(addHistory == true)
		{
		  history.pushState(stateObj, catNames[catId], link);
	  }
  }
  else
  {
    $.get( "/getpage", { category: catId, postId: postId, testmode: testMode }).done(function( data ) 
    {
      $(".content-frame").html(createPost(data, stateObj, link, addHistory));
			formatCodeBlock();
    });
  }
}

function createPostList(jsonArr)
{
  var params = jQuery.parseJSON(jsonArr);
  var data = "";
	var i = 0;
  
	if(params.data.length == 0)
	{
		data = data + "<br /><h4> No Posts Yet :( </h4><br />";
	}
	else
	{
		data = data + '<ul>';
		for(i=0;i<params.data.length;i++)
		{
			data = data + '<li class="postLink" onclick="getPage(' + params.data[i].catId + ',' + params.data[i].postId + ')">' + params.data[i].title + '</li>';
		}
		data = data + '</ul>';
  }
  
  return data;
}

function createPost(jsonArr, stateObj, link, addHistory)
{
  var params = jQuery.parseJSON(jsonArr);
  var data = "";
  
	data = data + '<div class="postDiv">';
	
  if(params.data.length != 0)
  {
	  var date = new Date(params.data[0].postedAt);
		data = data + '<div class="post-path">'  + '<span class="pathLink" onclick="getPage(0, 0)">Home</span>&nbsp;>>&nbsp;';
    data = data + '<span class="pathLink" onclick="getPage(' + params.data[0].catId + ', 0)">' + catNames[params.data[0].catId] + '</span>&nbsp;>>&nbsp;';
    data = data + '<span class="pathLink" onclick="getPage(' + params.data[0].catId +',' + params.data[0].postId +')">' + params.data[0].title + '</span></div>';
    data = data + '<div class="post-data">';
    data = data + '<div class="post-title">' + params.data[0].title + '</div>';
    data = data + '<span class="post-date">';
    data = data + '<span class="post-date-date">'  + date.getDate() + '</span><br />';
    data = data + '<span class="post-date-month">'  + monthNames[date.getMonth()] + '</span><br />';
    data = data + '<span class="post-date-year">'  + date.getFullYear() + '</span><br />';
    data = data + '</span>';
    data = data + '<span class="post-tags"> Tags&nbsp;:&nbsp;'  + params.data[0].tags + '</span>';
    data = data + '<div class="post-body">'  + params.data[0].content + '</div>';
    data = data + '</div>';
    data = data + '</div><br /><br />';
		link = link + params.data[0].link + '/';
  }
  else
  {
    data = data + '<div class="post-title"> OOPS! Someone made a boo-boo, bad developer! </div>';	
		link = link + "error";
  }
  
  data = data + '</div>';
	
	if(addHistory == true)
	{
	  history.pushState(stateObj, "", link);
  }
  
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
	if (next.length == 0) 
		next = $(elem+" img:first");
  }
  else {
  var next = active.prev();
	if (next.length == 0) 
		next = $(elem+" img:last");
  }
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
	clearInterval(bannerRotator);
  bannerRotator = setInterval("rotateBanners('"+elem+"',0)", 6000);
}