import { UserInboundDTO } from './user-inbound-dto';

export interface UserProfileInboundDTO {
  id: number;
  userData: UserInboundDTO;
  firstName: string;
  lastName: string;
  passwordLength: number;
}
