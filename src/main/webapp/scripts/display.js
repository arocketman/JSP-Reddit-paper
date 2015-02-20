function showUser(str){
            	topic = document.getElementById("topic").value;
            	nsfw = document.getElementById("nsfw").value;
            	eta = document.getElementById("eta").value;
                if (str==""){
                    return;
                }
                if (window.XMLHttpRequest){
                    // code for IE7+, Firefox, Chrome, Opera, Safari
                    xmlhttp=new XMLHttpRequest();
                }
                else{
                    // code for IE6, IE5
                    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
                }
                xmlhttp.onreadystatechange=function(){
                    if (xmlhttp.readyState==4 && xmlhttp.status==200){ 
                    	document.getElementById("wrapper").innerHTML=xmlhttp.responseText;
                    }
                }
                //send request
                xmlhttp.open("GET","Display?topic="+topic+"&eta="+eta+"&nsfw="+nsfw+"&ajax=true",true);
                xmlhttp.send();
}