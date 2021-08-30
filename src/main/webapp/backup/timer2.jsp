<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>타이머</title>
<style type="text/css">
div {
  font-family: Verdana, Arial, sans-serif;
  font-size: 25px;
  font-weight: bold;
  text-decoration: none;
}
</style>


</head>
<body>

<div class="countdown">00:00:00</div>
<input type="button" onclick="fncDelay();" value="Reset"/>
<hr/>

<script type="text/javascript">

       var tmhandler;
       /* Timer */
       function countdown( className, seconds ){
               var element, endTime, hours, mins, secs, msLeft, time;

               function updateTimer(){
                  msLeft = endTime - (+new Date);
                  if ( msLeft < 0 ) {
                      window.location = "logOut.jsp";
                  } else {
                      time = new Date( msLeft );
                      hours = ('0' + time.getUTCHours()).slice(-2);
                      mins  = ('0' + time.getUTCMinutes()).slice(-2);
                      secs  = ('0' + time.getUTCSeconds()).slice(-2);
                      element.innerHTML = hours + ':' + mins + ':' + secs;
                      tmhandler = setTimeout( updateTimer, time.getUTCMilliseconds());
                  }
               }

               element = document.getElementsByClassName(className);
               if(element.length!=0) {
                  element = element[0];
                  endTime = (+new Date) + 1000 * seconds;
                  updateTimer();
               } else {
                  alert("no timer");
               }
       }
       countdown('countdown', 150);  // seconds

       function fncDelay() {
               clearTimeout(tmhandler);
               countdown('countdown', 10);
       }
</script>

</body>
</html>