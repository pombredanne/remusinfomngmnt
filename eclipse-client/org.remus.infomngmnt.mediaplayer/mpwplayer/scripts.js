function swf(id,filename, width, height, flashvars, params, attributes)
{
	var arrayFlvars = new Array();
	for(var i in flashvars){ arrayFlvars.push(i+'='+flashvars[i]); }
	flashvars = arrayFlvars.join('&');
	
	var arrayAttrs = new Array();
	for(var i in attributes){ arrayAttrs.push(i + '="' + attributes[i] + '"'); }
	attributes = arrayAttrs.join(' ');
	
	var html = '<object id="'+id+'" width="'+width+'" height="'+height+'" type="application/x-shockwave-flash" data="'+filename+'"><param name="movie" value="'+filename+'?'+flashvars+'"></param>';
	var arrayParams = new Array();
	for(var i in params){
		html += '<param name="'+i+'" value="' + params[i] + '" />';
		arrayParams.push(i+'="'+params[i]+'"');
	}
	html += '<param name="flashvars" value="' + flashvars + '" />';
	params = arrayParams.join(' ');
	html += '</object>';
	return html;
}