function adminStartup()
{
  loadPosts();
  resizeMe();
}

function loadPosts()
{
    $.get("posts").done(function( data ) 
    {
      $(".adminPostList").html(createList(data));
    });
}

function submitAdminForm() {
	$("#adminform")
			.submit(
					function(event) {
						
						event.preventDefault();

						var $form = $( this ),
						  category  = $form.find('select[name="category"]').val(),
						  title = $form.find( 'textarea[name="title"]' ).val(),
						  link = $form.find( 'textarea[name="link"]' ).val(),
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
            else if ((link == "") || (link == " ")) 
						{
							alert("Link is required for the post");
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

function submitLoginForm(){
	$("#loginform")
	  .submit(
			function(event) {
				
				event.preventDefault();

				var $form = $( this ),
				userName = $form.find('input[name="username"]').val(),
				passWord = CryptoJS.SHA256($form.find( 'input[name="password"]' ).val()).toString(CryptoJS.enc.Hex),
				url = $form.attr( 'action' );
				
				$form.find( 'input[name="password"]' ).val(passWord);
				
				this.submit();
			});
 }
 
function sendLogout()
{
	  $("#adminform").find( 'input[name="logout"]' ).val("logout");
	  $("#adminform").submit();
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

function createList(posts)
{
  var params = jQuery.parseJSON(posts);
  var data = "";
	var i = 0;
  
	data = data + "<h3> Posts </h3>";
	
	if(params.data.length == 0)
	{
		data = data + "<br /><h4> No Posts Yet :( </h4><br />";
	}
	else
	{
		data = data + '<ul>';
		for(i=0;i<params.data.length;i++)
		{
			data = data + '<li class="postLink" onclick="getPost(' + params.data[i].catId + ',' + params.data[i].postId + ',' + params.data[i].testmode +')">' + params.data[i].title + '</li>';
		}
		data = data + '</ul>';
  }
  
  return data;
}

function getPost(catId, postId, testMode)
{
    $.get("getpage", { category: catId, postId: postId, testmode: testMode}).done(function( data ) 
    {
      updatePostForm(data);
    });
}

function updatePostForm(data)
{
  var params = jQuery.parseJSON(data);
	
	$("#adminform").find( 'textarea[name="title"]' ).val(params.data[0].title);
	$("#adminform").find( 'textarea[name="link"]' ).val(params.data[0].link);
	$("#adminform").find( 'textarea[name="tags"]' ).val(params.data[0].tags);
	$("#adminform").find( 'textarea[name="content"]' ).val(params.data[0].content);
	$("#adminform").find( 'select[name="category"]').val(params.data[0].catId);
	$("#adminform").find( 'input[name="postid"]').val(params.data[0].postId);
	if(params.data[0].testMode == true)
	{
	  $("#adminform").find( 'input[name="testmode"]').prop('checked', true);
	}
	else
	{
	  $("#adminform").find( 'input[name="testmode"]').prop('checked', false);
	}
	$("#adminform").find( 'input:radio[name=action][id=edit]').prop('checked', true);
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
  data = data + '<div class="post-body"><pre>'  + content + '</pre></div>';
  data = data + '</div>';
  data = data + '</div>';
	data = data + '</div>';
	
	$(".content-preview").css("padding", "3%");
	$(".content-preview").css("bottom", "1%");
	$(".content-preview").html(data);
	
	formatCodeBlock();
}

function closePreview()
{
  $(".content-preview").html("");
  $(".content-preview").css("padding", "0px");
	$(".content-preview").css("bottom", "100%");
}

function addNewLine()
{
	$('#content').selection('insert', {
			text: '<br />',
			mode: 'before'
	});
}

function addCodeBlock()
{
	$('#content').selection('insert', {
			text: '<div class="Code">',
			mode: 'before'
	});
	$('#content').selection('insert', {
			text: '</div>',
			mode: 'after'
	});
}

function addLinkTag()
{ 
  var linkUrl = $('#adminform').find('input[name="linkUrl"]').val();
  
  $('#content').selection('insert', {
			text: '<a class="inTextLink" href="' + linkUrl + '">',
			mode: 'before'
	});
	$('#content').selection('insert', {
			text: '</a>',
			mode: 'after'
	});
}

function addImageTag()
{
  var imgUrl   = $('#adminform').find('input[name="imgUrl"]').val();
  var thumbUrl = $('#adminform').find('input[name="thumbUrl"]').val();
  var imgName  = $('#adminform').find('input[name="imgName"]').val();
  
  var polImgLoc  = $('#adminform').find('input[name="polImgLoc"]').val();
  var polImgRot  = $('#adminform').find('input[name="polImgRot"]').val();
  
  $('#content').selection('insert', {
			text: '<div class="polImgDiv polImgLoc' + polImgLoc + ' polImgRot' + polImgRot' +" name="' + imgName + '"><img class="polImage" onClick="ShowImage(\'' + imgUrl + '\', \'Image\')" src="' + thumbUrl + '" alt="',
			mode: 'before'
	});
  $('#content').selection('insert', {
			text: '" /></div>',
			mode: 'after'
	});
}

function formatCodeBlock()
{
  var els = document.getElementsByClassName("Code");

  Array.prototype.forEach.call(els, function(c) 
	{
    var txt  = c.innerHTML;
    var ftxt = ""; 
    var letters = txt.split("");
    var i = 0;
    var j = 0;
    var endSpan = 0;
    var dq = 0;
    var sq = 0;
    var prev = 0; 
    var next = 0;
    var cmt = 0;
    var prevCmt = 0;
    var lineNum = 0;
    
    /* First Pass, check for quotes (single & double and enclose them in brown span) */ 
    while(letters[i] != null)
    { 
      /* When quotes are enabled no need to chec for comments */
      if((dq == 0) && (sq == 0))
      {
        /* In Other cases check for comments */
        
        next = 0;
        
        if(letters[i+1] != null)
        {
          next = letters[i+1][0];
        }
        
        if((next == '*') && (letters[i][0] == '/'))
        {
          if(cmt == 0)
          {
            cmt = 1;
          }
        }
        else if((letters[i][0] == '/') && (prev == '*'))
        {
          if(cmt == 1)
          {
            cmt = 0;
          }
        }      
        else if((next == '/') && (letters[i][0] == '/'))
        {
          if(cmt == 0)
          {
            cmt = 2;
          }
        }
        else if(letters[i][0] == '\n')
        {
          if(cmt == 2)
          {
            cmt = 0;
          }
        }
        else
        {
        }
      }
      
      if((cmt != 0) && (prevCmt == 0))
      {
        ftxt += '<span style="color: green;">';
      }
      else if((cmt == 0) && (prevCmt != 0))
      {
        endSpan = 1;
      }
      else
      {
      }
      
      /* When comments are not enabled check for quotes */
      if(cmt == 0)
      {
        if((letters[i][0] == '"') && (prev != "\\"))
        {
          if(sq == 0)
          {
            if(dq == 0)
            {
              ftxt += '<span style="color: brown;">';
              dq = 1;
            }
            else
            {
              endSpan = 1;            
              dq = 0;
            }
          }
        }
        else if((letters[i][0] == "'") && (prev != "\\"))
        {
          if(dq == 0)
          {
            if(sq == 0)
            {
              ftxt += '<span style="color: brown;">';
              sq = 1;
            }
            else
            {
              endSpan = 1;
              sq = 0;
            }
          }
        }
      }      
      
      prev = letters[i][0];
      ftxt += prev;
      prevCmt = cmt;
      
      if(endSpan != 0)
      {
        ftxt +=  '</span >';
        endSpan = 0;
      }
      
      i++;
    }
    
    var lines = ftxt.split(/\r\n|\r|\n/);
    ftxt = "";
    i = 0;
    
    while(lines[i] != null)
    {
      var words = lines[i].split(/ /);
      
      j = 0;
      
      while(words[j] != null)
      {
        if(words[j].search("<span") != -1)
        {
          endSpan = 3;
        }
        else if(words[j].search("</span") != -1)
        {
          endSpan = 0;
        }        
        else if(words[j][0] == '#')
        {
          if(endSpan < 2) 
          {         
            ftxt += '<span style="color: lightblue;">';
            endSpan = 1; 
          }
        }
        else if(words[j].search(/if|else|for|while|do|void|int|char|unsigned|signed|long|short|import|public|private|protected|String|class|return|this|static|throws|extends/) == 0)
        {
          if(endSpan < 2) 
          { 
            ftxt += '<span style="color: lightblue;">';
            endSpan = 1;
            
            if(words[j].search(/\(/) != -1)
            {
              var tokens = words[j].split(/\(/);
              var k = 0;
              
              words[j] = tokens[k++];
              words[j] += "</span>";
              
              while(tokens[k] != null)
              {
                words[j] += "(";
                words[j] += tokens[k++];
              }
                
              endSpan  = 0;
            }
          }        
        }
        else
        {
          if(endSpan < 2) { endSpan = 0; }
        }
        
        ftxt += words[j];
        ftxt += " ";
      
        if(endSpan == 1)
        {
          ftxt += "</span>";
        }
        
        j++;
      }
      
      ftxt += '\n  <span style="font-size: 72%; color: grey;">' + lineNum++;
      
      if(lineNum <= 10)
      {
        ftxt += "   ";
      }
      else if(lineNum <= 100)
      {
        ftxt += "  ";
      }
      else
      {
        ftxt += " ";
      }
      
      
      ftxt += "| </span>";
      
      if(endSpan < 2)
      {
        ftxt += "</span>";
      }
      
      i++;
    }
        
    c.innerHTML = ftxt;
  });
}