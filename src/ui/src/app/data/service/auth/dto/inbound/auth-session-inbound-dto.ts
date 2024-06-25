export interface AuthSessionInboundDto {
  token: string;
  username: string;
  id: string;
  email: string;
  roles: string[];
  signedInAtTime: number;
  expiresAtTime: number;
}
