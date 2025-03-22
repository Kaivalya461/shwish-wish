const express = require('express');
const path = require('path');

const app = express();

// Serve static files from the Angular build folder
app.use('/', express.static(path.join(__dirname, 'shwish-wish-app/dist/shwish-wish-app/browser')));

// Redirect all requests to Angular's index.html
app.get('/*', (req, res) => {
    res.sendFile(path.join(__dirname, 'shwish-wish-app/dist/shwish-wish-app/browser/index.html'));
});

// Start the server
const PORT = process.env.PORT || 8080;
app.listen(PORT, () => {
    console.log(`Server is running on http://localhost:${PORT}`);
});
