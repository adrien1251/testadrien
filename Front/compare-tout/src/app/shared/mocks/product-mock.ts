import { Product } from '../models/product.interface';
import { criteriaMock1, criteriaMock2 } from './critere-mock';

export const productMock: Product[] = [
    {
        description: 'Toboggan vague d\extérieur',
        name: 'Toboggan vague',
        imageUrl: './assets/images/toboggan1.jpg',
        criteriaList: criteriaMock1,
    },
    {
        description: 'Le toboggan des tout-petits',
        name: 'Mon 1er toboggan',
        imageUrl: './assets/images/toboggan2.jpg',
        criteriaList: criteriaMock2,
    },
];
