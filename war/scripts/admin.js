function submitAdminForm() {
	$("#adminform")
			.submit(
					function(event) {
						
						event.preventDefault();

						var $form = $( this ),
						  category  = $form.find('select[name="category"]').val(),
						  title = $form.find( 'textarea[name="title"]' ).val(),
						  tags   = $form.find( 'textarea[name="tags"]' ).val(),
						  content   = $form.find( 'textarea[name="content"]' ).val(),
						  testmode   = $('#testmode:checked').val();
						  url = $form.attr( 'action' );

						/* Validate the form */
						
						if ((category == "") || (category == " ")) 
						{
							alert("Please enter a category for the post");
						}
						else if ((title == "") || (title == " ")) 
						{
							alert("Title is required for the post");
						}
						else if ((tags == "") || (tags == " ")) 
						{
							alert("Tags are required for the post");
						}
						else if ((content == "") || (content == " ")) 
						{
							alert("Content is required for the post");
						}
						else 
						{
							this.submit();
						}
					});
}


function createPreview()
{
  var catNames = new Array("Home", "Travel", "Cooking", "Technology", "Entertainment");
	var monthNames = new Array("JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC");
  var category  = $("#adminform").find('select[name="category"]').val();
	var title = $("#adminform").find( 'textarea[name="title"]' ).val();
	var tags   = $("#adminform").find( 'textarea[name="tags"]' ).val();
	var content   = $("#adminform").find( 'textarea[name="content"]' ).val();
  var data = "";
	var date = new Date();
  
	data = data + '<div class="postDiv">';
  data = data + '<div class="post-path">'  + '<span class="pathLink" onclick="getPage(0, 0)">Home</span>&nbsp;>>&nbsp;';
  data = data + '<span class="pathLink" onclick="getPage(' + category + ', 0)">' + catNames[category] + '</span>&nbsp;>>&nbsp;';
  data = data + '<span class="pathLink" onclick="getPage(' + category +',0)">' + title + '</span></div>';
  data = data + '<div class="post-data">';
  data = data + '<div class="post-title">' + title + '</div>';
  data = data + '<div class="post-meta">';
  data = data + '<span class="post-date">';
	data = data + '<span class="post-date-date">'  + date.getDate() + '</span><br />';
	data = data + '<span class="post-date-month">'  + monthNames[date.getMonth()] + '</span><br />';
	data = data + '<span class="post-date-year">'  + date.getFullYear() + '</span><br />';
	data = data + '</span>';
  data = data + '<span class="post-tags"> Tags&nbsp;:&nbsp;'  + tags + '</span></div><br /><br />';
  data = data + '<div class="post-body">'  + content + '</div>';
  data = data + '</div>';
  data = data + '</div>';
	data = data + '</div>';
	
	$(".content-preview").css("padding", "3%");
	$(".content-preview").css("height", "100%");
	$(".content-preview").html(data);
}

function closePreview()
{
  $(".content-preview").html("");
  $(".content-preview").css("padding", "0px");
	$(".content-preview").css("height", "0px");
}