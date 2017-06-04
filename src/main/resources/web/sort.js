var sortController = (function sortController() {
	
	var resultCount = 0;

	return {

		callSort : function(toBeSorted) {

			var settings = {
				"async" : false,
				"crossDomain" : true,
				"url" : "/sort",
				"method" : "POST",
				"headers" : {
					"content-type" : "application/json",
					"cache-control" : "no-cache",
				},
				"processData" : false,
				"data" : "[" + toBeSorted + "]"
			}

			var result;
			$.ajax(settings).done(function(response) {
				console.log(response);
				result = response;
			});

			return result;
		},

		prepareDiv : function(divCopy, sortResult) {
			resultCount = resultCount + 1;

			divCopy.attr('id', 'sortResult_' + resultCount);

			divCopy.children('p').attr('id', 'description_' + resultCount);
			divCopy.children('p').text(
					'Sortering nummer ' + resultCount + ': ' + sortResult.swaps
							+ ' byten på ' + sortResult.elapsedTime
							+ ' millisekunder.');

			var row = divCopy.children('.row');
			var unsortedDiv = row.children('#unsorted_0');
			unsortedDiv.attr('id', 'unsorted_' + resultCount);
			unsortedDiv.val(sortResult.unsortedList.join());
			var sortedDiv = row.children('#sorted_0');
			sortedDiv.attr('id', 'sorted_' + resultCount);
			sortedDiv.val(sortResult.sortedList.join());

			var removeButton = divCopy.children('#button_remove_0');
			removeButton.attr('id', 'button_remove_' + resultCount);
			removeButton.click(function() {
				divCopy.slideUp();
			});

			return divCopy;
		},

		loadPreviousSort : function() {
			var settings = {
				"async" : false,
				"crossDomain" : true,
				"url" : "/oldsort",
				"method" : "GET",
				"headers" : {
					"content-type" : "application/json",
					"cache-control" : "no-cache",
				}		
			}

			var result;
			$.ajax(settings).done(function(response) {
				console.log(response);
				result = response;
			});

			return result;
		}

	}
})();