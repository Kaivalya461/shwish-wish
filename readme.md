### Deployment Steps (GCP Cloud Run)
## API
```
gcloud run deploy shwish-wish-api \
  --image=kaivalya461/shwish-wish \
  --region=us-central1 \
  --allow-unauthenticated \
  --set-env-vars=secret=SECRET_KEY,mail-username=SENDER_MAIL_ID,mail-password=SENDER_MAIL_PASS,mail-recipient=RECIPIENT_MAIL_ID
```

## UI
1. Checkout the source code.
2. Navigate to "./ui/express-server" directory.
3. Run below GCP command
```
gcloud run deploy shwish-wish-ui
```
4. Just Press 'Enter' when prompted to choose source code location (This selects current directory as root path of source code)
5. Select 'us-central1' location
6. Done