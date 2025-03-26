export class UserGeoLocationResponseDto {
    validLocation: boolean;
    latitudeReceived: number;
    longitudeReceived: number;

    constructor(validLocation: boolean, latitudeReceived: number, longitudeReceived: number) {
        this.validLocation = validLocation;
        this.latitudeReceived = latitudeReceived;
        this.longitudeReceived = longitudeReceived;
    }
}