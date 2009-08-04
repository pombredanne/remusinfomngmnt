
/**
 * <p>
 * Resizes an image that
 * is referenced with
 * <pre>
 * 	<img name="imageName">
 * </pre>
 * </p>
 * @param percentageWidth the new width
 * @param percentageHeight the new height
 * @param imageName The name of the image.
 */
var toggleState = false; 
var lastWidth;

function resizeImage(percentageWidth, percentageHeight, imageName) {
	
	document.images[imageName].height = percentageHeight;
	document.images[imageName].width = percentageWidth;
}
function resizeImage(percentageWidth, imageName) {
	document.images[imageName].width = percentageWidth;
}
function fit2Page(imageName) {
	document.images[imageName].width = document.body.clientWidth -10;
}
function toggleFit2Page(imageName) {
	if (!toggleState) {
		lastWidth = document.images[imageName].width;
		fit2Page(imageName);
	} else {
		document.images[imageName].width = lastWidth;
	}
	toggleState = !toggleState;
}
