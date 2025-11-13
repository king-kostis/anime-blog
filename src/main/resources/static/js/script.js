const fadeInElements = document.querySelectorAll('.fade-in-section');
const button = document.getElementById('menuButton');
const menu = document.getElementById('menuItems');

button.addEventListener('click', () => {
  if (menu.classList.contains('hidden')) {
    // Show menu
    menu.classList.remove('hidden');
    menu.classList.add('max-h-40'); // adjust to fit your items
  } else {
    // Hide menu
    menu.classList.add('hidden');
    menu.classList.remove('max-h-40');
  }
});

// Define the callback function for the observer
const observerCallback = (entries, observer) => {
    entries.forEach(entry => {
        // If the element is intersecting
        if (entry.isIntersecting) {
            entry.target.classList.add('is-visible');
        }
        else {
            entry.target.classList.remove('is-visible');
        }

    });
};

// Create a new IntersectionObserver instance
const observer = new IntersectionObserver(observerCallback, {
    root: null, // observe relative to the viewport
    rootMargin: '0px',
    threshold: 0.1 // Trigger when 10% of the element is visible
});

// Start observing all target elements
fadeInElements.forEach(element => {
    observer.observe(element);
});