function loadMore(str){
            	topic = document.getElementById("topic").value;
            	nsfw = document.getElementById("nsfw").value;
            	eta = document.getElementById("eta").value;
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
                    	if(str=== undefined)
                    		document.getElementById("wrapper").innerHTML=xmlhttp.responseText;
                    	else{
                    		$("#tempLoading").remove();
                    		$("#wrapper").append(xmlhttp.responseText);
                    	}
                    }
                }
                if (str=== undefined){
                    xmlhttp.open("GET","Display?topic="+topic+"&eta="+eta+"&nsfw="+nsfw+"&ajax=true",true);
                }
                else{
                    xmlhttp.open("GET","Display?topic="+topic+"&eta="+eta+"&nsfw="+nsfw+"&ajax=true&after=true",true);
                }
                //send request
                xmlhttp.send();

                $(window).bind('scroll', bindScroll);
}


