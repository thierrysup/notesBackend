(function(b){var a={extern:null,append:true,width:320,height:240,mode:"callback",swffile:"jscam.swf",quality:85,debug:function(){},onCapture:function(){},onTick:function(){},onSave:function(){},onLoad:function(){}};window.webcam=a;b.fn.webcam=function(d){if(typeof d==="object"){for(var c in a){if(d[c]!==undefined){a[c]=d[c]}}}var e='<object id="XwebcamXobjectX" type="application/x-shockwave-flash" data="'+a.swffile+'" width="'+a.width+'" height="'+a.height+'"><param name="movie" value="'+a.swffile+'" /><param name="FlashVars" value="mode='+a.mode+"&amp;quality="+a.quality+'" /><param name="allowScriptAccess" value="always" /></object>';if(null!==a.extern){b(a.extern)[a.append?"append":"html"](e)}else{this[a.append?"append":"html"](e)}(_register=function(g){var f=document.getElementById("XwebcamXobjectX");if(f.capture!==undefined){a.capture=function(h){try{return f.capture(h)}catch(i){}};a.save=function(h){try{return f.save(h)}catch(i){}};a.setCamera=function(h){try{return f.setCamera(h)}catch(i){}};a.getCameraList=function(){try{return f.getCameraList()}catch(h){}};a.onLoad()}else{if(0==g){a.debug("error","Flash movie not yet registered!")}else{window.setTimeout(_register,1000*(4-g),g-1)}}})(3)}})(jQuery);PrimeFaces.widget.PhotoCam=PrimeFaces.widget.BaseWidget.extend({init:function(b){this._super(b);this.form=this.jq.parents("form:first");this.url=this.form.attr("action");this.canvas=document.createElement("canvas");this.canvas.setAttribute("width",320);this.canvas.setAttribute("height",240);this.ctx=this.canvas.getContext("2d"),this.image=this.ctx.getImageData(0,0,320,240);this.pos=0;var a=this;this.jq.webcam({width:320,height:240,mode:"callback",swffile:this.cfg.camera,onSave:function(c){a.save(c)},onCapture:function(){webcam.save()},debug:function(d,c){console.log(d+": "+c)}})},save:function(d){var a=d.split(";");for(var c=0;c<320;c++){var b=parseInt(a[c]);this.image.data[this.pos+0]=(b>>16)&255;this.image.data[this.pos+1]=(b>>8)&255;this.image.data[this.pos+2]=b&255;this.image.data[this.pos+3]=255;this.pos+=4}if(this.pos>=4*320*240){this.ctx.putImageData(this.image,0,0);$.ajax({url:this.url,type:"POST",cache:false,dataType:"xml",data:this.createPostData(),success:function(f,e,g){PrimeFaces.ajax.Response.handle.call(this,f,e,g)}});this.pos=0}},createPostData:function(){var a=this.form.serialize(),c={},b=this.cfg.process?this.cfg.process+" "+this.id:this.id;c[PrimeFaces.PARTIAL_REQUEST_PARAM]=true;c[PrimeFaces.PARTIAL_PROCESS_PARAM]=b;c[PrimeFaces.PARTIAL_SOURCE_PARAM]=this.id;c[this.id]=this.canvas.toDataURL("image/png");if(this.cfg.update){c[PrimeFaces.PARTIAL_UPDATE_PARAM]=this.cfg.update}a=a+"&"+$.param(c);return a},capture:function(){webcam.capture()}});