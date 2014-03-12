function startUp()
{
    resizeMe();
	/* getPage(0, 0); */
	startRotator("#home-content-scroller");
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
	$.post( "getPage/", { catId: catId, postId: postId }).done(function( data ) 
	{
		alert( "Data Loaded: " + data );
	});
}

function rotateBanners(elem) {
  var active = $(elem+" img.active");
  var next = active.next();
  if (next.length == 0) 
    next = $(elem+" img:first");
  active.removeClass("active").fadeOut(1000);
  next.addClass("active").fadeIn(1000);
}
 
function prepareRotator(elem) {
  $(elem+" img").fadeOut(0);
  $(elem+" img:first").fadeIn(0).addClass("active");
}
 
function startRotator(elem) {
  prepareRotator(elem);
  setInterval("rotateBanners('"+elem+"')", 6000);
}