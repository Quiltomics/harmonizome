var HMZ = function(config) {

	/* Code executes here
	 * --------------------------------------------------------------------- */
	config = config || {};
	setupDataTables();
	setupShowByGroupFunctionality();
	//showAttributesByDataset($('.dataset-row'));
	if (config.UP_GENES) {
		setupDownloadLinks(config.UP_GENES);
		setupEnrichrLink(config.UP_GENES);
		
	}
	if (config.DOWN_GENES) {
		setupDownloadLinks(config.DOWN_GENES);
		setupEnrichrLink(config.DOWN_GENES);
	}
	if (config.ALL_GENES) {
		setupGeneSearch(config.ALL_GENES);
	}
	/* --------------------------------------------------------------------- */

	/* Utility function for getting the list of genes from the page.
	 */
	function getGeneSet($el) {
		var genes = [],
			firstRowSkipped = false;
		_.each($el.find('tr'), function(tr) {
			if (firstRowSkipped) {
				var symbol = $(tr).find('td a').html();
				genes.push(symbol);
			}
			firstRowSkipped = true;
		});
		return genes;
	};
	
	/* Bind events for showing genes and attributes by group.
	 */
	function setupShowByGroupFunctionality() {
		var $showHideButtons = $('.dataset-row .glyphicon');
		$showHideButtons.click(function(evt) {
			var datasetGroup = $(evt.target).parent().attr('data-dataset-group');
			showItemsByDatasetGroup(datasetGroup);
		});
	};
	
	/* Show genes or attributes by dataset group.
	 */
	function showItemsByDatasetGroup(group) {
		var $group = $('.' + group),
			$items = $group.next(),
			$plusButton = $group.find('.glyphicon.glyphicon-plus'),
			$minusButton = $group.find('.glyphicon.glyphicon-minus');
		if ($items.hasClass('active')) {
			$items.fadeOut();
		} else {
			$items.fadeIn();
		}
		$plusButton.toggleClass('hidden');
		$minusButton.toggleClass('hidden');
		$items.toggleClass('active');
	};

	/* Setup datasets table if it exists.
	 */
	function setupDataTables() {
		if (!_.isUndefined($().dataTable)) {
			$('.data-table').dataTable({
				bPaginate: true,
				iDisplayLength: 25
			});
		}
	};

	/* Utility function for building names for filenames and Enrichr descriptions.
	 */
	function getDescription() {
		return ['harmonizome', ATTRIBUTE_NAME, DATASET_NAME].join('_');
	};

	/* Bind events for sending data to Enrichr.
	 */
	function setupEnrichrLink(genes) {
		$('.enrichr').click(function(evt) {
			enrich({
				description: getDescription(),
				list: genes
			})
		});
	};
	
	/* Send data to Enrichr.
	 */
	function enrich(options) {
		var defaultOptions = {
			description: ""
		};
		
		$.extend(defaultOptions, options);

		var form = document.createElement('form');
		form.setAttribute('method', 'post');
		form.setAttribute('action', 'http://amp.pharm.mssm.edu/Enrichr/enrich');
		form.setAttribute('target', '_blank');
		form.setAttribute('enctype', 'multipart/form-data');

		var listField = document.createElement('input');
		listField.setAttribute('type', 'hidden');
		listField.setAttribute('name', 'list');
		listField.setAttribute('value', options.list);
		form.appendChild(listField);

		var descField = document.createElement('input');
		descField.setAttribute('type', 'hidden');
		descField.setAttribute('name', 'description');
		descField.setAttribute('value', options.description);
		form.appendChild(descField);
		
		document.body.appendChild(form);
		form.submit();
		document.body.removeChild(form);
	};
	
	/* Utility function for a pure JavaScript download function.
	 */
	function download(filename, text) {
	    var pom = document.createElement('a');
	    pom.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(text));
	    pom.setAttribute('download', filename);
	    if (document.createEvent) {
	        var event = document.createEvent('MouseEvents');
	        event.initEvent('click', true, true);
	        pom.dispatchEvent(event);
	    } else {
	        pom.click();
	    }
	};
	
	/* Setup download links on gene page.
	 */
	function setupDownloadLinks(genes) {
		$('.glyphicon-download-alt').click(function(evt) {
			download(getDescription() + ".txt", genes.join("\n"));
		});
	};
	
	function setupGeneSearch(genes) {
		var $input = $('input')
			.first()
			.autocomplete({
				minLength: 3,
				source: genes
			})
			.keypress(function(evt) {
				if (evt.which === 13) {
					loadGenePage();
				}
			});
		$(':submit').click(loadGenePage);
		
		function loadGenePage(evt) {
			if (evt) {
				evt.originalEvent.preventDefault();
			}
			window.location.href = "gene/" + $input.val();
		};
	};
};