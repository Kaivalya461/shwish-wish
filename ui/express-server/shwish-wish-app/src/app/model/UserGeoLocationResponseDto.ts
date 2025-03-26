export class UserGeoLocationResponseDto {
    isValidLocation: boolean;
    latitudeReceived: number;
    longitudeReceived: number;

    constructor(isValidLocation: boolean, latitudeReceived: number, longitudeReceived: number) {
        this.isValidLocation = isValidLocation;
        this.latitudeReceived = latitudeReceived;
        this.longitudeReceived = longitudeReceived;
    }
}