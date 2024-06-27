import { ProductInboundDTO } from './product-inbound-dto';

export interface ProductPageInboundDTO {
  pageIndex: number;
  totalPagesAmount: number;
  pageElementCount: number;
  content: ProductInboundDTO[];
}
