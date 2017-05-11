(function($) {
	var CameraSensorComponent = function(element, options) {
		this.init(element, options);
	};
	CameraSensorComponent.prototype = {
		init : function(element, options) {
			var me = this;

			me.setOptions(element, options);

			me.drawComponentContent();

		},
		setOptions : function(element, options) {
			this.options = $.extend({}, (this.options || $.fn.cameraSensorComponent.defaults), options);
			this.$element = $(element);
			$(element).attr({
						id : this.options.id
					});
		},
		drawComponentContent : function() {
			var me = this;
			var map = me.options.mrl.split(',');
			var rowsArray = [];
			if (map.length == 1) {
				var itemPanel = me.createItemPanel('100%', '100%', map[0]);
				rowsArray.push(itemPanel)
			} else {
				var rowsArray = [];
				var iwidth = Math.floor((document.body.scrollWidth-40) / 2); // 每一列的宽度
				var iheight = Math.floor((document.body.scrollHeight-20) / 2);
				for (var i = 0; i < map.length; i++) {
					var itemPanel = me.createItemPanel(iwidth, iheight, map[i]);
					rowsArray.push(itemPanel);
				}
			}
			me.$element.append(rowsArray.join(''));
		},
		createItemPanel : function(iwidth, iheight, vlc) {
			var me = this;
			var id = vlc;
			var b_id = vlc+"_b";
			return '<div id="button_bar" class="videotop" style="display:none;"><a id="'+b_id+'" class="b_Stream1" href="javascript:;" onclick="connectMain()" >主码流</a></div>'
					+ '<object id="'+id+'" type="application/media-plugin-version-3.0.0.1" width="'+iwidth+'" height="'+iheight+'" style="background-color:#000000;margin-left:10px;margin-up:10px;"></object>';
		
		}
	};
	$.fn.cameraSensorComponent = function(options) {
		new CameraSensorComponent(this, options)
	};
	$.fn.cameraSensorComponent.defaults = {
		id : new Date().getTime()
	};
	$.fn.cameraSensorComponent.Constructor = CameraSensorComponent;
}(window.jQuery))

var mrl = $('#mrl').attr('code');
$('.container').cameraSensorComponent({
			mrl : mrl
		});
