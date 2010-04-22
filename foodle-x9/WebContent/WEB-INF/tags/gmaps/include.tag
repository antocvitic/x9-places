<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false" ></script>
<script type="text/javascript">
  function addressMap(id, address) {
    var myOptions = {
      zoom: 15,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    var map = new google.maps.Map(document.getElementById(id), myOptions);
    map.getDiv().map = map;
    map.getDiv().markers = new Array();
    var geocoder = new google.maps.Geocoder();
    if (geocoder) {
      geocoder.geocode( { 'address': address}, function(results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
          map.setCenter(results[0].geometry.location);
          var marker = new google.maps.Marker({
              map: map, 
              position: results[0].geometry.location
          });
          map.getDiv().markers.push(marker);
        } else {
          alert("Geocode was not successful for the following reason: " + status);
        }
      });
    }
  }
  
  function coordMap(id, lat, lng) {
	var latlng = new google.maps.LatLng(lat, lng);
    var myOptions = {
      zoom: 15,
      center: latlng,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    var map = new google.maps.Map(document.getElementById(id), myOptions);
    map.getDiv().map = map;
    map.getDiv().markers = new Array();
  }

  function centerMap(id, address) {
	    var myOptions = {
	      zoom: 15,
	      mapTypeId: google.maps.MapTypeId.ROADMAP
	    };
	    var map = new google.maps.Map(document.getElementById(id), myOptions);
	    map.getDiv().map = map;
	    map.getDiv().markers = new Array();
	    var geocoder = new google.maps.Geocoder();
	    if (geocoder) {
	      geocoder.geocode( { 'address': address}, function(results, status) {
	        if (status == google.maps.GeocoderStatus.OK) {
	          map.setCenter(results[0].geometry.location);
	        } else {
	          alert("Geocode was not successful for the following reason: " + status);
	        }
	      });
	    }
	  }
  
  function addInfoMarker(id, address, title, info) {
	var map;
	if( document.getElementById(id) == null ) {
		alert("No div matching id");
		/*var div = document.createElement('div');
		div.setAttribute('id', id);
		div.style.width = 500;
		div.style.height = 500;
		div.innerHTML='dick';
		document.body.appendChild(div);
		
		var latlng = new google.maps.LatLng(-25.363882,131.044922);
		var myOptions = {
	      zoom: 15,
	      center: latlng,
	      mapTypeId: google.maps.MapTypeId.ROADMAP
	    };
		map = new google.maps.Map(document.getElementById(id), myOptions);*/
	} else {
		map = document.getElementById(id).map;
	}
	
	var geocoder = new google.maps.Geocoder();
	var marker;
    if (geocoder) {
    	
      geocoder.geocode( { 'address': address}, function(results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
          marker = new google.maps.Marker({
			map: map,
            position: results[0].geometry.location,
            title: title
          });
          map.getDiv().markers.push(marker);
          
          var infowindow = new google.maps.InfoWindow({
            content: info
          });
          
          google.maps.event.addListener(marker, 'click', function() {
            infowindow.open(map, marker);
          });

          var bounds = new google.maps.LatLngBounds();
          for (var i = 0; i < map.getDiv().markers.length; i++) {
          	bounds.extend(map.getDiv().markers[i].getPosition());
          }
          map.fitBounds(bounds);
        } else {
          alert("Geocode was not successful for the following reason: " + status);
        }
      });
    }
  }  
</script>