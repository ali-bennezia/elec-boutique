import { UserInboundDTO } from '../../../service/auth/dto/inbound/user-inbound-dto';

export interface ProductInboundDTO {
  id: number;
  name: string;
  description: string;
  tags: string;
  price: number;
  medias: string[];
  user: UserInboundDTO;
  createdAtTime: number;
  averageNote: number;
}
