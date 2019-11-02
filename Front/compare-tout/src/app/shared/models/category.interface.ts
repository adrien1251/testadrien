import { Criteria } from './criteria.interface';
import { Product } from './product.interface';

export interface Category {
    id: string;
    name: string;
    parent?: Category;
    childList?: Category[];
    criteriaList?: Criteria[];
    productList?: Product[];
}
