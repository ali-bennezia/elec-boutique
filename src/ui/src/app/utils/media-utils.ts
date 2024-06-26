import { environment } from 'src/environments/environment';

export class MediaUtils {
  static getMediaUri(media: string) {
    return `${environment.backendUri}/api/medias/${media}`;
  }

  static getProfilePhotoMediaUri(media: string | null) {
    return media != null
      ? this.getMediaUri(media)
      : '/assets/images/guest_user.png';
  }
}
