import { Criteria } from './criteria.interface';

export interface Product {
    id: string;
    description: string;
    name: string;
    imageUrl: string;
    criteriaList: Criteria[];
}
