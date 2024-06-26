export interface UserInboundDTO {
  id: number;
  username: string;
  profilePhotoMedia: string | null;
  enabled: boolean;
  createdAtTime: number;
  roles: string[];
  businessName: string;
}
