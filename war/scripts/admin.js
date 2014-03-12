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