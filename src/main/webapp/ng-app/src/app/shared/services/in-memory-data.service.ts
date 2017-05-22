/**
 * Created by fran lopez on 14/05/2017.
 */

import {InMemoryDbService} from 'angular-in-memory-web-api';

export class InMemoryDataService implements InMemoryDbService {
    createDb() {
        let customers = [
            {
                mobile: 600000000,
                username: 'customer1',
                dni: '66666666A',
                email: 'customer1@gmail.com',
                address: 'address1',
                password: 'password1',
                active: true
            },
            {
                mobile: 600000001,
                username: 'customer2',
                dni: '66666666B',
                email: 'customer2@gmail.com',
                address: 'address2',
                password: 'password2',
                active: false
            },
            {
                mobile: 600000002,
                username: 'customer3',
                dni: '66666666C',
                email: 'customer3@gmail.com',
                address: 'address2',
                password: 'password2',
                active: false
            },
            {
                mobile: 600000003,
                username: 'customer4',
                dni: '66666666D',
                email: 'customer4@gmail.com',
                address: 'address3',
                password: 'password3',
                active: true
            },
            {
                mobile: 600000004,
                username: 'customer5',
                dni: '66666666E',
                email: 'customer5@gmail.com',
                address: 'address4',
                password: 'password4',
                active: false
            },
            {
                mobile: 600000005,
                username: 'customer6',
                dni: '66666666F',
                email: 'customer6@gmail.com',
                address: 'address5',
                password: 'password5',
                active: true
            },
            {
                mobile: 600000006,
                username: 'customer7',
                dni: '66666666G',
                email: 'customer7@gmail.com',
                address: 'address6',
                password: 'password6',
                active: true
            },
            {
                mobile: 600000007,
                username: 'customer8',
                dni: '66666666H',
                email: 'customer8@gmail.com',
                address: 'address7',
                password: 'password7',
                active: true
            },
            {
                mobile: 600000008,
                username: 'customer9',
                dni: '66666666I',
                email: 'customer9@gmail.com',
                address: 'address8',
                password: 'password8',
                active: true
            },
            {
                mobile: 600000009,
                username: 'customer10',
                dni: '66666666J',
                email: 'customer10@gmail.com',
                address: 'address9',
                password: 'password9',
                active: true
            },
            {
                mobile: 600000010,
                username: 'customer11',
                dni: '66666666K',
                email: 'customer11@gmail.com',
                address: 'address10',
                password: 'password10',
                active: true
            },
            {
                mobile: 600000011,
                username: 'customer12',
                dni: '66666666L',
                email: 'customer12@gmail.com',
                address: 'address10',
                password: 'password10',
                active: true
            },
            {
                mobile: 600000012,
                username: 'customer13',
                dni: '66666666M',
                email: 'customer13@gmail.com',
                address: 'address11',
                password: 'password11',
                active: true
            },
            {
                mobile: 600000013,
                username: 'customer14',
                dni: '66666666N',
                email: 'customer14@gmail.com',
                address: 'address12',
                password: 'password12',
                active: true
            },
            {
                mobile: 600000014,
                username: 'customer15',
                dni: '66666666O',
                email: 'customer15@gmail.com',
                address: 'address13',
                password: 'password13',
                active: true
            },
            {
                mobile: 600000015,
                username: 'customer16',
                dni: '66666666P',
                email: 'customer16@gmail.com',
                address: 'address14',
                password: 'password14',
                active: true
            },
        ];
        let operators = [
            {
                mobile: 600000000,
                username: 'operator1',
                dni: '66666666A',
                email: 'operator1@gmail.com',
                address: 'address1',
                password: 'password1',
                active: true
            },
            {
                mobile: 600000001,
                username: 'operator2',
                dni: '66666666B',
                email: 'operator2@gmail.com',
                address: 'address2',
                password: 'password2',
                active: false
            },
            {
                mobile: 600000002,
                username: 'operator3',
                dni: '66666666C',
                email: 'operator3@gmail.com',
                address: 'address2',
                password: 'password2',
                active: false
            },
            {
                mobile: 600000003,
                username: 'operator4',
                dni: '66666666D',
                email: 'operator4@gmail.com',
                address: 'address3',
                password: 'password3',
                active: true
            },
            {
                mobile: 600000004,
                username: 'operator5',
                dni: '66666666E',
                email: 'operator5@gmail.com',
                address: 'address4',
                password: 'password4',
                active: false
            },
            {
                mobile: 600000005,
                username: 'operator6',
                dni: '66666666F',
                email: 'operator6@gmail.com',
                address: 'address5',
                password: 'password5',
                active: true
            },
            {
                mobile: 600000006,
                username: 'operator7',
                dni: '66666666G',
                email: 'operator7@gmail.com',
                address: 'address6',
                password: 'password6',
                active: true
            },
            {
                mobile: 600000007,
                username: 'operator8',
                dni: '66666666H',
                email: 'operator8@gmail.com',
                address: 'address7',
                password: 'password7',
                active: true
            },
            {
                mobile: 600000008,
                username: 'operator9',
                dni: '66666666I',
                email: 'operator9@gmail.com',
                address: 'address8',
                password: 'password8',
                active: true
            },
            {
                mobile: 600000009,
                username: 'operator10',
                dni: '66666666J',
                email: 'operator10@gmail.com',
                address: 'address9',
                password: 'password9',
                active: true
            },
            {
                mobile: 600000010,
                username: 'operator11',
                dni: '66666666K',
                email: 'operator11@gmail.com',
                address: 'address10',
                password: 'password10',
                active: true
            },
            {
                mobile: 600000011,
                username: 'operator12',
                dni: '66666666L',
                email: 'operator12@gmail.com',
                address: 'address10',
                password: 'password10',
                active: true
            },
            {
                mobile: 600000012,
                username: 'operator13',
                dni: '66666666M',
                email: 'operator13@gmail.com',
                address: 'address11',
                password: 'password11',
                active: true
            },
            {
                mobile: 600000013,
                username: 'operator14',
                dni: '66666666N',
                email: 'operator14@gmail.com',
                address: 'address12',
                password: 'password12',
                active: true
            },
            {
                mobile: 600000014,
                username: 'operator15',
                dni: '66666666O',
                email: 'operator15@gmail.com',
                address: 'address13',
                password: 'password13',
                active: true
            },
            {
                mobile: 600000015,
                username: 'operator16',
                dni: '66666666P',
                email: 'operator16@gmail.com',
                address: 'address14',
                password: 'password14',
                active: true
            },
        ];
        return {customers, operators};
    }
}