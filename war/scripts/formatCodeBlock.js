
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
              ftxt += '<span style="color: lightgreen;">';
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
              ftxt += '<span style="color: lightgreen;">';
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
    
    while(i < lines.length)
    {
      var words = lines[i].split(/ /);
      
      j = 0;
      
      ftxt += '<span style="font-size: 72%; color: grey;">' + lineNum++;
      
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
      
      while(j < words.length)
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
      
      if(endSpan < 2)
      {
        ftxt += "</span>";
      }
      
      ftxt += "\n";
      
      i++;
    }
        
    c.innerHTML = ftxt;
  });
}

function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results == null ? false : decodeURIComponent(results[1].replace(/\+/g, " "));
}