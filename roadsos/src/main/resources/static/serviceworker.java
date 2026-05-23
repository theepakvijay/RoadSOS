//
//public class serviceworker {
//	const CACHE_NAME = 'roadsos-v1';
//
//	// Files to cache on install
//	const STATIC_ASSETS = [
//	  '/',
//	  '/index.html'
//	];
//
//	// Install — cache static files
//	self.addEventListener('install', event => {
//	  event.waitUntil(
//	    caches.open(CACHE_NAME).then(cache => {
//	      return cache.addAll(STATIC_ASSETS);
//	    })
//	  );
//	  self.skipWaiting();
//	});
//
//	// Activate — clean old caches
//	self.addEventListener('activate', event => {
//	  event.waitUntil(
//	    caches.keys().then(keys =>
//	      Promise.all(
//	        keys.filter(key => key !== CACHE_NAME)
//	            .map(key => caches.delete(key))
//	      )
//	    )
//	  );
//	  self.clients.claim();
//	});
//
//	// Fetch — serve from cache when offline
//	self.addEventListener('fetch', event => {
//	  const url = new URL(event.request.url);
//
//	  // API requests — network first, cache as fallback
//	  if (url.pathname.startsWith('/api/')) {
//	    event.respondWith(
//	      fetch(event.request)
//	        .then(response => {
//	          // Save fresh API response to cache
//	          const copy = response.clone();
//	          caches.open(CACHE_NAME).then(cache => {
//	            cache.put(event.request, copy);
//	          });
//	          return response;
//	        })
//	        .catch(() => {
//	          // Offline — return cached API response
//	          return caches.match(event.request);
//	        })
//	    );
//	    return;
//	  }
//
//	  // HTML/CSS/JS — cache first, network as fallback
//	  event.respondWith(
//	    caches.match(event.request).then(cached => {
//	      return cached || fetch(event.request);
//	    })
//	  );
//	});
//
//}
