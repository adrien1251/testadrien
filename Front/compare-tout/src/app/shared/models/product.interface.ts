import { Criteria } from './criteria.interface';

export interface Product {
    id: string;
    description: string;
    descriptionShort: string;
    name: string;
    imageUrl: string;
    criteriaDtoList: Criteria[];
}
