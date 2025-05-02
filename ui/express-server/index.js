const express = require('express');
const path = require('path');

const app = express();

// Serve static files from the Angular build folder with caching disabled for index.html
app.use('/', express.static(path.join(__dirname, 'shwish-wish-app/dist/shwish-wish-app/browser'), {
    setHeaders: (res, path) => {
        if (path.endsWith('index.html')) {
            res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            res.setHeader("Pragma", "no-cache");
            res.setHeader("Expires", "0");
        }
    }
}));

// Redirect all requests to Angular's index.html with caching headers
app.get('/*', (req, res) => {
    res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    res.setHeader("Pragma", "no-cache");
    res.setHeader("Expires", "0");
    res.sendFile(path.join(__dirname, 'shwish-wish-app/dist/shwish-wish-app/browser/index.html'));
});

// Start the server
const PORT = process.env.PORT || 8080;
app.listen(PORT, () => {
    console.log(`Server is running on http://localhost:${PORT}`);
});