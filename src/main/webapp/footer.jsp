<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script> 
<script src="scripts/jquery.magnific-popup.min.js"></script>

<script>$('.test-popup-image').magnificPopup({ 
	  type: 'image'
			// other options
		});
		
$('.test-popup-link').magnificPopup({ 
	  type: 'iframe'
			// other options
		});
		

function bindScroll(){
	if(document.location.href.match("Display")){
	   if($(window).scrollTop() + $(window).height() > $(document).height() - 100) {
		   if($("#tempLoading").length === 0)
	       	$("#wrapper").last().append('<div id="tempLoading"><img style="display:block;margin:0 auto;" src="images/loading_spinner.gif"></div>');
	       $(window).unbind('scroll');
	       loadMore("after");
	   }

	}
	}
	 
$(window).scroll(bindScroll);
</script>
</body>
</html>