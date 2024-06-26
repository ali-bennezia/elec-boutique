export interface AuthSessionInboundDto {
  token: string;
  username: string;
  profilePhotoMedia: string | null;
  id: string;
  email: string;
  roles: string[];
  signedInAtTime: number;
  expiresAtTime: number;
}
