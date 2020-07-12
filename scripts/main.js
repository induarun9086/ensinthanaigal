const insQuotes = [
  'You dont take a photograph, you make it <br /> - Ansel Adams',
  'There are no rules for good photographs, there are only good photographs. <br /> - Ansel Adams',
  'A photograph is a portrait painted by the sun <br /> - unknown',
  'A work of art which did not begin in emotion is not art <br /> - Paul Cezanne',
  'Great art picks up where nature ends <br /> - Marc Chagall',
  'Art is not what you see, but what you make others see <br /> - Edgar Degas',
  'A Good artist has less time than ideas <br /> - Martin Kippenberger',
  'I saw the angel in the marble and carved until I set him free <br /> - Michelangelo'
];

function showImage(imgId) {
  document.getElementById("imgStageImg").src = document.getElementById(imgId).src;
  document.getElementById("imgStage").style.display = "block";
}

function hideImage() {
  document.getElementById("imgStage").style.display = "none";
  document.getElementById("imgStageImg").src = "";
}

/* Onload init function */
function onLoadFn() {
  new Vue({
        el: "#vueApp",
        data: {
          currLevel: -1,
          urlParts: [],
          currCatTitle: "Home",
          loading: true,
          postList: [],
          listReq: false,
          postData: [],
          dataReq: false,
          lastErr: -1,
          currHomeImgIdx: 0,
          numHomeImgs: 5,
          homeImgTimer: null
        },
        methods: {
          loadUrl: function(url, pushSt=true) {
            /* If the state is to be pushed */
            if(pushSt == true) {
              /* Push current URL to history */
              window.history.pushState(null, null, url);
            }
            
            /* Clear data */
            this.postList.length = 0;
            this.postData.length = 0;
            this.lastErr = -1;
            this.currLevel = -1;
              
            /* Check if this is a specifc post request or a intermediate path request */
            if((url == "") || (url.charAt(url.length - 1) == '/')) {
              this.listReq = true;
              this.dataReq = false;
              document.title = "என் சிந்தனைகள் - A Sweet Trail of my memories";
            } else {
              this.listReq = false;
              this.dataReq = true;
            }
            
            /* Find out the number of the levels in the path */
            this.urlParts = url.split("/");
            this.urlParts = this.urlParts.filter(f => (f != ""));
            this.currLevel = this.urlParts.length;
            
            /* Load the data */
            this.loadData();
          },
          loadPost: function(post) {
            var url = "/" + post.cats[0] + "/" + post.url;
            this.loadUrl(url);
          },
          getDateTimeStr: function(ts) {
            var dt = new Date(ts);
            const days = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
            const months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
            const h = dt.getHours();
            const m = dt.getMinutes();
            
            var str = days[dt.getDay()] + ', ' + dt.getDate() + ' ' + months[dt.getMonth()] + ' ' + dt.getFullYear() + ', ';
            str += ((h < 10) ? ('0' + h):(h));
            str += ':' + ((m < 10) ? ('0' + m):(m));
            
            return str;
          },
          getTSDate: function(ts) {
            var dt = new Date(ts);
            return dt.getDate();
          },
          getTSMonthStr: function(ts) {
            const months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
            var dt = new Date(ts);
            return months[dt.getMonth()];
          },
          getTSYear: function(ts) {
            var dt = new Date(ts);
            return dt.getFullYear();
          },
          popState: function(e) {
            this.loadUrl(window.location.pathname, false);
          },
          getThumbUrl: function(tUrl) {
            if((tUrl == "") || (tUrl == undefined)) {
              return "/imgs/gears.png";
            } else {
              return tUrl;
            }
          },
          capitalize: function(s) {
            if (typeof s !== 'string') return ''
            return s.charAt(0).toUpperCase() + s.slice(1)
          },
          getErrStr: function() {
            var str = "";
            switch(this.lastErr) {
              case 1:
                str = "No posts for this category";
                break;
              case 2:
                str = "Requested post not found";
                break;
              default:
                str = "HTTP Error " + this.lastErr;
                break;
            }
            return str;
          },
          nextHomeImg: function() {
            this.currHomeImgIdx++;
          },
          loadData: function() {
            /* load lists */
            if(this.listReq == true) {
              this.loading = true;
              var vueObj = this;
              var xhr = new XMLHttpRequest();
              var filReq = "";
              if(this.currLevel == 0) {
                this.homeImgTimer = setInterval(this.nextHomeImg, 5000);
              } else {
                clearInterval(this.homeImgTimer);
              }
              this.currCatTitle = this.capitalize(this.urlParts[0]);
              this.urlParts.forEach(function(u, i) {
                filReq += (((i != 0)?(','):('cat=')) + u);
              });
              xhr.open("GET", ("/getPostList?" + filReq));
              xhr.onreadystatechange = function() {
                if(this.readyState == 4) {
                  if((this.status == 200) || (this.status == 304)) {
                    vueObj.postList = JSON.parse(this.responseText);
                    if(vueObj.postList.length > 0) {
                      vueObj.lastErr = 0;
                    } else {
                      vueObj.lastErr = 1;
                    }
                  } else {
                    vueObj.lastErr = this.status;
                  }
                  vueObj.loading = false;
                } 
              }
              xhr.send(); 
            /* load post */
            } else if(this.dataReq == true) {
              this.loading = true;
              var vueObj = this;
              var xhr = new XMLHttpRequest();
              xhr.open("GET", ("/post" + window.location.pathname));
              xhr.onreadystatechange = function() {
                if(this.readyState == 4) {
                  if((this.status == 200) || (this.status == 304)) {
                    var postData = JSON.parse(this.responseText);
                    if(postData.length > 0) {
                      postData[0].data = vueObj.formatCodeBlocks(postData[0].data);
                      vueObj.postData = postData;
                      vueObj.lastErr  = 0;
                      document.title  = vueObj.postData[0].title;
                    } else {
                      vueObj.lastErr = 2;
                    }
                  } else {
                    vueObj.lastErr = this.status;
                  }
                  vueObj.loading = false;
                } 
              }
              xhr.send(); 
            } else {
              /* Do nothing */
            }
          },
          formatCodeBlocks: function(data) {
            var fcode = data;
            
            for(var i = 0; i < 10; i++) {
              let re = new RegExp('<pre(.*?)codeBlock' + i + '(.*?)>([\\s\\S]*?)<\/pre>');
              let code = re.exec(fcode);
              if((code != null) && (code.length == 4)) {
                fcode = fcode.replace(re, '<pre class="codeBlock" id="codeBlock' + i + '">' + formatCodeBlock(code[3]) + '</pre>');
              }
            }
            
            return fcode;
          }
        },
        computed: {
          randomQuote: function() {
            var rStr = "";
            
            if((this.loading == false) && (this.lastErr == 0)) {
              var rIdx = Math.round(Math.random() * insQuotes.length);
              rStr = insQuotes[rIdx];
            }
            
            return rStr;
          }
        },
        mounted: function() {
          this.loadUrl(window.location.pathname);
        },
        created: function() {
          window.addEventListener('popstate',this.popState);
        },
        destroyed: function() {
          window.removeEventListener('popstate', this.popState);
        }
  });
}