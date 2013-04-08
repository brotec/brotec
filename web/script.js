var map;
var geocoder;
var marker;

var circleA;
var circleB;
var markerA;
var markerB;
var draw_circle = null;

var directionDisplay;
var directionsService = new google.maps.DirectionsService();


function initialize() {
				directionsDisplay = new google.maps.DirectionsRenderer();
    
    
    var latlng = new google.maps.LatLng(-23.700, -46.500);
    var options = {
        zoom: 10,
        center: latlng,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };

    map = new google.maps.Map(document.getElementById("map"), options);

    geocoder = new google.maps.Geocoder();

    marker = new google.maps.Marker({
        map: map
    });

    markerA = new google.maps.Marker({
        map: map
    });

    markerB = new google.maps.Marker({
        map: map
    });
    
    directionsDisplay.setMap(map);
    directionsDisplay.setPanel(document.getElementById('directions'));

}

function calcRoute() {
        var start = document.getElementById('routeA').value;
        var end = document.getElementById('routeB').value;
        var request = {
          origin: start,
          destination: end,
          travelMode: google.maps.DirectionsTravelMode.DRIVING
        };
        directionsService.route(request, function(response, status) {
          if (status == google.maps.DirectionsStatus.OK) {
            directionsDisplay.setDirections(response);
          }
        });
      }

$(document).ready(function() {

    initialize();

    $(function() {
        $("#address, #circleA, #circleB, #routeA, #routeB").autocomplete({

            source: function(request, response) {
                geocoder.geocode({ 'address': request.term }, function(results, status) {
                    response($.map(results, function(item) {
                        return {
                            label: item.formatted_address,
                            value: item.formatted_address,
                            latitude: item.geometry.location.lat(),
                            longitude: item.geometry.location.lng()
                        }
                    }));
                })
            }
        });

        $("#address").autocomplete({
            select: function(event, ui) {
                var location = new google.maps.LatLng(ui.item.latitude, ui.item.longitude);
                marker.setPosition(location);
                map.panTo(location);
                map.setZoom(13);
                
                if (draw_circle != null) {
                    draw_circle.setMap(null);
                }
            }
        });

        $("#circleA").autocomplete({
            select: function(event, ui) {
                circleA = new google.maps.LatLng(ui.item.latitude, ui.item.longitude);
                markerA.setPosition(circleA);
                map.panTo(circleA);
                if (circleB) {
                    
                    var rad = google.maps.geometry.spherical.computeDistanceBetween(circleA, circleB);
                    
                    if (draw_circle != null) {
                        draw_circle.setMap(null);
                    }

                    draw_circle = new google.maps.Circle({
                        center: circleA,
                        radius: rad,
                        strokeColor: "#FF0000",
                        strokeOpacity: 0.8,
                        strokeWeight: 2,
                        fillColor: "#FF0000",
                        fillOpacity: 0.35,
                        map: map
                    });
                    map.fitBounds(draw_circle.getBounds());
                }
            }
        });

        $("#circleB").autocomplete({
            select: function(event, ui) {
                circleB = new google.maps.LatLng(ui.item.latitude, ui.item.longitude);
                markerB.setPosition(circleB);
                map.panTo(circleB);
                
                if (circleA) {

                    var rad = google.maps.geometry.spherical.computeDistanceBetween(circleA, circleB);

                    if (draw_circle != null) {
                        draw_circle.setMap(null);
                    }

                    draw_circle = new google.maps.Circle({
                        center: circleA,
                        radius: rad,
                        strokeColor: "#FF0000",
                        strokeOpacity: 0.8,
                        strokeWeight: 2,
                        fillColor: "#FF0000",
                        fillOpacity: 0.35,
                        map: map
                    });
                    map.fitBounds(draw_circle.getBounds());
                }
            }
        });
        
        $("#routeA, #routeB").autocomplete({
            select: function(event, ui) {
                calcRoute();
            }
        });

    });
});