import { Product } from '../models/product.interface';
import { criteriaMock1, criteriaMock2 } from './critere-mock';

export const productMock: Product[] = [
    {
        description: 'un super téléphone',
        name: 'Nokia 3310',
        imageUrl: 'https://drop.ndtv.com/TECH/product_database/images/2152017124957PM_635_nokia_3310.jpeg',
        criteriaList: criteriaMock1,
    },
    {
        description: 'un téléphone bien trop cher',
        name: 'Apple',
        imageUrl: 'https://d1fmx1rbmqrxrr.cloudfront.net/cnet/i/edit/2019/07/iphone-11-XI-prix-sortie.jpg',
        criteriaList: criteriaMock2,
    },
];
